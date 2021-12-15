package com.example.demolocalstack.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.Data;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Data
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.demolocalstack.dynamodb.repositories")
public class AwsDynamoDbConfig {
    @Value("${config.aws.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${config.aws.dynamodb.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${config.aws.dynamodb.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${cloud.aws.region.static}")
    private String region;


    @Autowired
    private ApplicationContext context;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {

       return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(getEndpointConfiguration(amazonDynamoDBEndpoint))
               .withCredentials(new AWSCredentialsProvider() {
                   @Override
                   public AWSCredentials getCredentials() {
                       return amazonAWSCredentials();
                   }

                   @Override
                   public void refresh() {

                   }
               })
                .build();



    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(String url) {
        return new AwsClientBuilder.EndpointConfiguration(url, region);
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean(name = "mvcHandlerMappingIntrospectorCustom")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospectorCustom() {
        return new HandlerMappingIntrospector(context);
    }
}
