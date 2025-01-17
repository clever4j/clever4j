package com.clever4j.valuetype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegerValueTest {

    @Test
    public void requireValue() {
        IntegerValue value = IntegerValue.of(1);

        assertEquals(1, value.requireValue());
    }

    @Test
    public void add() {
        IntegerValue valueA = IntegerValue.of(1);
        IntegerValue valueB = IntegerValue.of(2);

        assertEquals(3, valueA.add(valueB).requireValue());
    }

    @Test
    public void multiple() {
        IntegerValue valueA = IntegerValue.of(1);
        IntegerValue valueB = IntegerValue.of(2);

        assertEquals(25, IntegerValue.of(5).multiple(IntegerValue.of(5)).requireValue());
        assertEquals(150, IntegerValue.of(5).multiple(IntegerValue.of(30)).requireValue());
    }

    @Test
    public void equals() {
        assertFalse(IntegerValue.of(1).equals(IntegerValue.of(2)));
        assertTrue(IntegerValue.of(2).equals(IntegerValue.of(2)));
    }
}
