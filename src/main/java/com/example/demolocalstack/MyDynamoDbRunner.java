package com.example.demolocalstack;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.demolocalstack.dynamodb.ProductInfo;
import com.example.demolocalstack.dynamodb.repositories.ProductInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MyDynamoDbRunner implements CommandLineRunner {

  @Autowired
  ProductInfoRepository repository;

  @Autowired
  private AmazonDynamoDB amazonDynamoDB;

  @Override
  public void run(String... args) throws Exception {
    DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    String cost = "100";
    String price = "100";
    ProductInfo productInfo = new ProductInfo(cost, price);
    repository.save(productInfo);
    List<ProductInfo> result = (List<ProductInfo>) repository.findAll();
    result.forEach(
        p -> {
          log.info(p.getId());
          log.info(p.getCost());
          log.info(p.getMsrp());
        });
      dynamoDBMapper.batchDelete(repository.findAll());
      result = (List<ProductInfo>) repository.findAll();
      log.info(String.valueOf(result.size()));
  }
}
