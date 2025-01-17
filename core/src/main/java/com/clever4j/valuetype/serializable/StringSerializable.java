package com.clever4j.valuetype.serializable;

import jakarta.annotation.Nullable;

public interface StringSerializable {

    @Nullable
    String serializeToString();
}
