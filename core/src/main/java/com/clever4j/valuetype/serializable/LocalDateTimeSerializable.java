package com.clever4j.valuetype.serializable;

import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public interface LocalDateTimeSerializable {

    @Nullable
    LocalDateTime serializeToLocalDateTime();
}
