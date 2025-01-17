package com.clever4j.s3;

import com.clever4j.lang.ResourceUtil;
import com.clever4j.s3.BucketHandler.BucketObjects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BucketHandlerTest {

    static final String NAME = "traisit-storage";
    static final String ACCESS_KEY_ID = "";
    static final String SECRET_ACCESS_KEY = "";
    static final String REGION = "eu-central-1";
    static final String PREFIX = "s3/";
    static final String DELIMITER = "/";

    private final BucketHandler bucketHandler = new BucketHandler(NAME, ACCESS_KEY_ID, SECRET_ACCESS_KEY, REGION, PREFIX, DELIMITER);

    @Test
    void list() {
        initBucket();

        BucketObjects list = bucketHandler.list();

        assertEquals(2, list.size());
        assertEquals("s3/file-a.txt", list.getFirst().getS3Object().key());
        assertEquals("s3/file-b.txt", list.getLast().getS3Object().key());
    }

    @Test
    void listWithPrefix() {
        initBucket();

        BucketObjects list = bucketHandler.list("sub/");

        assertEquals(2, list.size());
        assertEquals("s3/sub/file-sub-a.txt", list.getFirst().getS3Object().key());
        assertEquals("s3/sub/file-sub-b.txt", list.getLast().getS3Object().key());
    }

    @Test
    void listRecursive() {
        initBucket();

        BucketObjects list = bucketHandler.list(true);

        assertEquals(4, list.size());
    }

    private void initBucket() {
        bucketHandler.put("file-a.txt", requireNonNull(ResourceUtil.getByteArray("s3/file-a.txt")));
        bucketHandler.put("file-b.txt", requireNonNull(ResourceUtil.getByteArray("s3/file-b.txt")));

        bucketHandler.put("sub/file-sub-a.txt", requireNonNull(ResourceUtil.getByteArray("s3/sub/file-sub-a.txt")));
        bucketHandler.put("sub/file-sub-b.txt", requireNonNull(ResourceUtil.getByteArray("s3/sub/file-sub-b.txt")));
    }
}