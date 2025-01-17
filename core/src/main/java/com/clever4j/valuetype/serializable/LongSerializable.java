package com.clever4j.valuetype.serializable;

import jakarta.annotation.Nullable;

public interface LongSerializable {

    @Nullable
    Long serializeToLong();
}