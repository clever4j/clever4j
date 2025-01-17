package com.clever4j.valuetype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongValueTest {

    @Test
    public void requireValue() {
        LongValue value = LongValue.of(1L);

        assertEquals(1, value.requireValue());
    }

    @Test
    public void add() {
        LongValue valueA = LongValue.of(1L);
        LongValue valueB = LongValue.of(2L);

        assertEquals(3, valueA.add(valueB).requireValue());
    }

    @Test
    public void multiple() {
        LongValue valueA = LongValue.of(1L);
        LongValue valueB = LongValue.of(2L);

        assertEquals(25, LongValue.of(5L).multiple(LongValue.of(5L)).requireValue());
        assertEquals(150, LongValue.of(5L).multiple(LongValue.of(30L)).requireValue());
    }

    @Test
    public void equals() {
        assertFalse(LongValue.of(1L).equals(LongValue.of(2L)));
        assertTrue(LongValue.of(2L).equals(LongValue.of(2L)));
    }
}
