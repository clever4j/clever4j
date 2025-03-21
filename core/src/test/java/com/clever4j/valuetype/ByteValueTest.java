package com.clever4j.valuetype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ByteValueTest {

    @Test
    void testByteConversions() {
        ByteValue value = ByteValue.of(1024L * 1024 * 1024); // 1 GB in bytes

        assertEquals(1024L * 1024 * 1024, value.getBytes());
        assertEquals(1024L * 1024, value.getKilobytes());
        assertEquals(1024L, value.getMegabytes());
        assertEquals(1L, value.getGigabytes());
        assertEquals(0L, value.getTerabytes());
    }

    @Test
    void testNullValue() {
        ByteValue value = ByteValue.of(null); // 1 GB in bytes

        assertNull(value.getBytes());
        assertNull(value.getKilobytes());
        assertNull(value.getMegabytes());
        assertNull(value.getGigabytes());
        assertNull(value.getTerabytes());
    }

    @Test
    void testDefaultValues() {
        ByteValue value = ByteValue.of(null); // 1 GB in bytes

        assertEquals(100L, value.getBytesOrDefault(100L));
        assertEquals(1L, value.getKilobytesOrDefault(1024L));
        assertEquals(1L, value.getMegabytesOrDefault(1024L * 1024L));
    }

    @Test
    void testZeroFallback() {
        ByteValue value = ByteValue.of(null); // 1 GB in bytes

        assertEquals(0L, value.getBytesOrZero());
        assertEquals(0L, value.getKilobytesOrZero());
        assertEquals(0L, value.getMegabytesOrZero());
        assertEquals(0L, value.getGigabytesOrZero());
        assertEquals(0L, value.getTerabytesOrZero());
    }
}