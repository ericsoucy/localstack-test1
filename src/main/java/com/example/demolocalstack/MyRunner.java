package com.example.demolocalstack;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.demolocalstack.s3.S3DTO;
import com.example.demolocalstack.s3.S3Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class MyRunner implements CommandLineRunner {

  @Autowired private S3Repository repository;

  @Override
  public void run(String... args) throws Exception {
    String bucketName = "my-shitty-bucket-terraform";
    String filename = "somefile-to-upload.txt";

    repository.listObjectsInBucket(bucketName);

      String somekey = "somekey";
      try (FileInputStream fileInputStream = new FileInputStream(new File(filename))) {
      repository.save(bucketName, somekey, filename, fileInputStream);
    }
    List<S3DTO> objects = repository.listObjectsInBucket(bucketName);
    objects.forEach(
        o -> {
          log.info(o.getKey());
          log.info(o.getName());
          log.info(o.getUrl().toString());
        });
    S3ObjectInputStream downloadedFile = repository.downloadFileFromS3Bucket(bucketName, somekey);
    byte[] bytes = downloadedFile.readAllBytes();
    log.info(new String(bytes, StandardCharsets.UTF_8));

    repository.delete(bucketName, somekey);
    repository.listObjectsInBucket(bucketName);
  }
}
