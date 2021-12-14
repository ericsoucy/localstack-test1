package com.example.demolocalstack.s3;

import lombok.Builder;
import lombok.Value;

import java.net.URL;


@Value
@Builder
public class S3DTO {
    String name;
    String key;
    URL url;
    @Builder.Default boolean isPublic = false;
}