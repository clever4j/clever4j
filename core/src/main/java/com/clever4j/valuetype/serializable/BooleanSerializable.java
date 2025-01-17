package com.clever4j.valuetype.serializable;

import jakarta.annotation.Nullable;

public interface BooleanSerializable {

    @Nullable
    Boolean serializeToBoolean();
}
