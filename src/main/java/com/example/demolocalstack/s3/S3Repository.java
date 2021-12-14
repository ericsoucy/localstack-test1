package com.example.demolocalstack.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.waiters.WaiterParameters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class S3Repository {
    private final AmazonS3Client s3Client;

    public S3Repository(AmazonS3Client s3Client){
        this.s3Client = s3Client;
    }

    public void create(String bucket) {
        // send bucket creation request
        s3Client.createBucket(bucket);
        log.info("Request to create " + bucket + " sent");

        // assure that bucket is available
        s3Client.waiters().bucketExists().run(new WaiterParameters<>(new HeadBucketRequest(bucket)));
        log.info("Bucket " + bucket + " is ready");
    }

    public void makePublic(String bucket, String key) {
        s3Client.setObjectAcl(bucket, key, CannedAccessControlList.PublicRead);
        log.info("Sent request to make object in bucket " + bucket + " with key " + key + " public");
    }

    public void makePrivate(String bucket, String key) {
        s3Client.setObjectAcl(bucket, key, CannedAccessControlList.BucketOwnerFullControl);
        log.info("Sent request to make object in bucket " + bucket + " with key " + key + " private");
    }

    public void delete(String bucket) {
        // send deletion request
        s3Client.deleteBucket(bucket);
        log.info("Request to delete " + bucket + " sent");

        // assure bucket is deleted
        s3Client.waiters().bucketNotExists().run(new WaiterParameters(new HeadBucketRequest(bucket)));
        log.info("Bucket " + bucket + " is deleted");
    }

    public void delete(String bucket, String key) {
        s3Client.deleteObject(bucket, key);
        log.info("Sent request to delete file with key " + key + " in bucket " + bucket);
    }

    public S3DTO save(String bucket, String key, String name, InputStream payload) {
        var metadata = new ObjectMetadata();
        metadata.addUserMetadata("name", name);
        s3Client.putObject(bucket, key, payload, metadata);
        log.info("Sent the request");
        return S3DTO.builder().name(name).key(key).url(s3Client.getUrl(bucket, key)).build();
    }

    public List<S3DTO> listObjectsInBucket(String bucket) {
        var items =
                s3Client.listObjectsV2(bucket).getObjectSummaries().stream()
                        .parallel()
                        .map(S3ObjectSummary::getKey)
                        .map(key -> mapS3ToObject(bucket, key))
                        .collect(Collectors.toList());

        log.info("Found " + items.size() + " objects in the bucket " + bucket);
        return items;
    }

    private S3DTO mapS3ToObject(String bucket, String key) {

        return S3DTO.builder()
                .name(s3Client.getObjectMetadata(bucket, key).getUserMetaDataOf("name"))
                .key(key)
                .url(s3Client.getUrl(bucket, key))
                .isPublic(
                        s3Client.getObjectAcl(bucket, key).getGrantsAsList().stream()
                                .anyMatch(grant -> grant.equals(S3Repository.publicObjectReadGrant())))
                .build();
    }

    private static Grant publicObjectReadGrant() {
        return new Grant(
                GroupGrantee.parseGroupGrantee(GroupGrantee.AllUsers.getIdentifier()), Permission.Read);
    }

    public void setVisibility(String bucket, Integer ttlInDays) {
        s3Client.setBucketLifecycleConfiguration(
                bucket,
                new BucketLifecycleConfiguration()
                        .withRules(
                                new BucketLifecycleConfiguration.Rule()
                                        .withId("custom-expiration-id")
                                        .withFilter(new LifecycleFilter())
                                        .withStatus(BucketLifecycleConfiguration.ENABLED)
                                        .withExpirationInDays(ttlInDays)));
    }

    public void removeVisibility(String bucket) {
        s3Client.deleteBucketLifecycleConfiguration(bucket);
    }

    public URL createURL(String bucket, String key, Long duration) {
        var date = new Date(new Date().getTime() + duration * 1000); // 1 s * 1000 ms/s
        var url = s3Client.generatePresignedUrl(bucket, key, date);
        log.info("Generated the signature " + url);
        return url;
    }
}
