package com.clever4j.s3;

import com.clever4j.lang.ResourceUtil;
import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;

class BucketHandlerTest {

    static final String NAME = "traisit-storage";
    static final String ACCESS_KEY_ID = "";
    static final String SECRET_ACCESS_KEY = "";
    static final String REGION = "eu-central-1";
    static final String PREFIX = "s3/";
    static final String DELIMITER = "/";

    @Test
    void crud() {
        BucketHandler bucketHandler = new BucketHandler(NAME, ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION, PREFIX, DELIMITER);

        bucketHandler.put("file-a.txt", requireNonNull(ResourceUtil.getByteArray("s3/file-a.txt")));
        bucketHandler.put("file-b.txt", requireNonNull(ResourceUtil.getByteArray("s3/file-b.txt")));

        bucketHandler.put("sub/file-sub-a.txt", requireNonNull(ResourceUtil.getByteArray("s3/sub/file-sub-a.txt")));
        bucketHandler.put("sub/file-sub-b.txt", requireNonNull(ResourceUtil.getByteArray("s3/sub/file-sub-b.txt")));
    }
}