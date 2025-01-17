package com.clever4j.valuetype;

public enum MimeType {
    APPLICATION_JSON("application/json");

    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;

    }
}