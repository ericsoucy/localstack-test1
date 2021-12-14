package com.example.demolocalstack.s3;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AwsS3Config {
  @Value("${config.aws.s3.url}")
  private String s3EndpointUrl;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean(name = "amazonS3")
  public AmazonS3 amazonS3() {
    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(getEndpointConfiguration(s3EndpointUrl))
        .build();
  }

  private EndpointConfiguration getEndpointConfiguration(String url) {
    return new EndpointConfiguration(url, region);
  }
}
