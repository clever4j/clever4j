package com.clever4j.valuetype;

public enum MimeType {
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_ZIP("application/zip"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    AUDIO_MPEG("audio/mpeg"),
    VIDEO_MP4("video/mp4"),
    TEXT_CSS("text/css"),
    TEXT_JAVASCRIPT("text/javascript");

    private final String contentType;

    MimeType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
