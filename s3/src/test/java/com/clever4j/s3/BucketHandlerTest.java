package com.clever4j.s3;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BucketHandlerTest {

    @Test
    void crud() {
        // BucketHandler bucketHandler = new BucketHandler();
        // BucketObjects bucketObjects = bucketHandler.list();

        bucketHandler.putObject("Screenshot from 2024-12-31 14-22-17.png", Path.of("/tmp/Screenshot from 2024-12-31 14-22-17.png"));

        // try {
        //     Files.copy(bucketObjects.toList().getFirst().getInputStream(), Path.of("/tmp/test.zip"));
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }
    }
}