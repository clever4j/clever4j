package com.clever4j.s3;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@AllNonnullByDefault
public final class BucketHandler implements AutoCloseable {

    private final String bucket;
    private final String accessKeyId;
    private final String secretAccessKey;
    private final String region;
    private final String prefix;
    private final String delimiter;
    private final Object clientMutex = new Object();

    @Nullable
    private S3Client client;

    @Nullable
    private S3Presigner presigner;

    public BucketHandler(String bucket, String accessKeyId, String secretAccessKey, String region, String prefix, String delimiter) {
        this.bucket = bucket.strip();
        this.accessKeyId = accessKeyId.strip();
        this.secretAccessKey = secretAccessKey.strip();
        this.region = region.strip();
        this.prefix = prefix.strip();
        this.delimiter = delimiter.strip();
    }

    private S3Client getClient() {
        synchronized (clientMutex) {
            if (client != null) {
                return client;
            }

            AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

            return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
        }
    }

    @Override
    public void close() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    private S3Presigner getPresigner() {
        return S3Presigner.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    public URL getPresignedUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(5))
            .getObjectRequest(getObjectRequest)
            .build();

        return getPresigner()
            .presignGetObject(presignRequest)
            .url();
    }

    // private final String iamRole;
    // private final String region;
    // private final String prefix;
    // private final String delimiter;
    // private final PathTokenizer pathTokenizer;
    // private final List<BucketObject> objects = new ArrayList<>();

    // @Nullable
    // private S3Client client = null;

    //    public BucketHandler(String name, String accessKeyId, String secretAccessKey, String iamRole, String region, String prefix, String delimiter) {
//        // this.name = name;
//        // this.accessKeyId = accessKeyId;
//        // this.secretAccessKey = secretAccessKey;
//        // this.iamRole = iamRole;
//        // this.region = region;
//        // this.prefix = prefix;
//        // this.delimiter = delimiter;
//        // this.pathTokenizer = new PathTokenizer(delimiter);
//    }
//
//    // S3Client --------------------------------------------------------------------------------------------------------
//    private static S3Client createClient(String region) {
//        return S3Client.builder()
//            .region(Region.of(region))
//            .build();
//    }
//
    //
//    // private static S3Client createClient(String accessKeyId, String secretAccessKey, String region, String role) {
//    //     String session = UUID.randomUUID().toString();
//
//    //     BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
//
//    //     AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
//    //         .withCredentials(new AWSStaticCredentialsProvider(credentials))
//    //         .withRegion(region)
//    //         .build();
//
//    //     AssumeRoleRequest roleRequest = new AssumeRoleRequest()
//    //         .withRoleArn(role)
//    //         .withRoleSessionName(session);
//
//    //     AssumeRoleResult roleResult = stsClient.assumeRole(roleRequest);
//
//    //     AwsSessionCredentials sessionCredentials = AwsSessionCredentials.create(
//    //         roleResult.getCredentials().getAccessKeyId(),
//    //         roleResult.getCredentials().getSecretAccessKey(),
//    //         roleResult.getCredentials().getSessionToken()
//    //     );
//
//    //     return S3Client.builder()
//    //         .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
//    //         .region(Region.of(region))
//    //         .build();
//    // }
//
//    // private S3Client createClient() {
//    //     if (isBlank(accessKeyId) && isBlank(secretAccessKey) && isBlank(iamRole)) {
//    //         return createClient(region);
//    //     } else if (StringUtils.isNotBlank(iamRole)) {
//    //         return createClient(accessKeyId, secretAccessKey, region, iamRole);
//    //     } else {
//    //         return createClient(accessKeyId, secretAccessKey, region);
//    //     }
//    // }
//
//    private synchronized S3Client getClient() {
//        // if (client == null) {
//        //     this.client = createClient();
//        // }
//
//        // return this.client;
//    }
//
//    private BucketObject createBucketObject(S3Object s3Object) {

    /// /
    /// /        String key = s3Object.key().replaceFirst(prefix, "");
    /// /
    /// /        PathTokenizer.PathParts pathParts = pathTokenizer.getPathParts(key);
    /// /
    /// /        return new BucketObject(
    /// /            S3ObjectKey.of(key),
    /// /            pathParts.parent(),
    /// /            pathParts.name(),
    /// /            pathParts.extension(),
    /// /            pathParts.fileName(),
    /// /            s3Object
    /// /        );
//
//        return null;
//    }
//
//    // exists ----------------------------------------------------------------------------------------------------------
//    public boolean exists(BucketObject object) {
//        return this.objects.stream().anyMatch(object1 -> {
//            return object1.getKey().equals(object.getKey());
//        });
//    }
//
//    // getObjects ------------------------------------------------------------------------------------------------------
//    public List<BucketObject> getObjects() {
//        List<BucketObject> objects = new ArrayList<>();
//
//        ListObjectsV2Response listObjectsResponse;
//        String continuationToken = null;
//
//        do {
//            ListObjectsV2Request request = ListObjectsV2Request.builder()
//                .bucket(name)
//                .prefix(prefix)
//                .continuationToken(continuationToken)
//                .build();
//
//            listObjectsResponse = getClient().listObjectsV2(request);
//            continuationToken = listObjectsResponse.nextContinuationToken();
//
//            for (S3Object s3Object : listObjectsResponse.contents()) {
//                objects.add(createBucketObject(s3Object));
//            }
//
//        } while (continuationToken != null);
//
//        return objects;
//    }
//
    // list ------------------------------------------------------------------------------------------------------------
    public BucketObjects list() {
        return list(null, false);
    }

    public BucketObjects list(boolean recursive) {
        return list(null, recursive);
    }

    public BucketObjects list(String prefix) {
        return list(prefix, false);
    }

    public BucketObjects list(@Nullable String prefix, boolean recursive) {
        List<BucketObject> objects = new ArrayList<>();

        ListObjectsV2Response response;
        String continuationToken = null;

        do {
            ListObjectsV2Request.Builder builder = ListObjectsV2Request.builder();

            builder.bucket(bucket);

            if (prefix != null) {
                builder.prefix(this.prefix + prefix);
            } else {
                builder.prefix(this.prefix);
            }

            if (!recursive) {
                builder.delimiter(delimiter);
            }

            builder.continuationToken(continuationToken);

            response = getClient().listObjectsV2(builder.build());
            continuationToken = response.nextContinuationToken();

            for (S3Object s3Object : response.contents()) {
                objects.add(new BucketObject(s3Object));
            }

        } while (continuationToken != null);

        return new BucketObjects(objects);
    }

    // put -------------------------------------------------------------------------------------------------------------
    public void put(String key, byte[] content) {
        S3Client client = getClient();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(this.prefix + key)
            .build();

        client.putObject(putObjectRequest, RequestBody.fromBytes(content));
    }

    public void put(String key, Path path) {
        S3Client client = getClient();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(this.prefix + key)
            .build();

        client.putObject(putObjectRequest, RequestBody.fromFile(path));
    }

    // public void copy(com.snapon.nissan.migrate.s3.S3Object object, Bucket destination, String destinationKey) {
    //     if (object == null) {
    //         System.out.printf("test");
    //     }

    //     CopyObjectRequest copyRequest = CopyObjectRequest.builder()
    //         .sourceBucket(name)
    //         .sourceKey(object.getKey())
    //         .destinationBucket(destination.getName())
    //         .destinationKey(destinationKey)
    //         .build();

    //     getClient().copyObject(copyRequest);
    // }

    // get -------------------------------------------------------------------------------------------------------------
    public InputStream getInputStream(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();

        return getClient().getObject(request);
    }

    public InputStream getInputStream(BucketObject object) {
        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(bucket)
            .key(object.getS3Object().key())
            .build();

        return getClient().getObject(request);
    }

    // BucketObject
    // -----------------------------------------------------------------------------------------------------------------
    public class BucketObject {

        private final S3Object s3Object;

        public BucketObject(S3Object s3Object) {
            this.s3Object = s3Object;
        }

        public S3Object getS3Object() {
            return s3Object;
        }

        public InputStream getInputStream() {
            return BucketHandler.this.getInputStream(this);
        }
    }

    // BucketObjects
    // -----------------------------------------------------------------------------------------------------------------
    public class BucketObjects implements Iterable<BucketObject> {

        private final List<BucketObject> objects;

        public BucketObjects(List<BucketObject> objects) {
            this.objects = objects;
        }

        @Override
        public Iterator<BucketObject> iterator() {
            return objects.iterator();
        }

        public List<BucketObject> toList() {
            return objects.stream().toList();
        }

        public Stream<BucketObject> stream() {
            return objects.stream();
        }

        public int size() {
            return objects.size();
        }

        public boolean isEmpty() {
            return objects.isEmpty();
        }

        public BucketObject get(int index) {
            return objects.get(index);
        }

        public BucketObject getFirst() {
            return objects.getFirst();
        }

        public BucketObject getLast() {
            return objects.getLast();
        }

        // public List<BucketObject> list() {
        //     return objects.stream()
        //         .filter(bucketObject -> !bucketObject.hasParent())
        //         .toList();
        // }
    }
}