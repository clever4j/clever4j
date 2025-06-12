package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class MimeTypeValue extends MimeTypeValueTemplate<MimeTypeValue> {

    public static final MimeTypeValue IMAGE_PNG = new MimeTypeValue("image/png");
    public static final MimeTypeValue IMAGE_JPEG = new MimeTypeValue("image/jpeg");
    public static final MimeTypeValue IMAGE_GIF = new MimeTypeValue("image/gif");
    public static final MimeTypeValue IMAGE_BMP = new MimeTypeValue("image/bmp");
    public static final MimeTypeValue IMAGE_SVG_XML = new MimeTypeValue("image/svg+xml");
    public static final MimeTypeValue IMAGE_WEBP = new MimeTypeValue("image/webp");
    public static final MimeTypeValue APPLICATION_JSON = new MimeTypeValue("application/json");
    public static final MimeTypeValue APPLICATION_XML = new MimeTypeValue("application/xml");
    public static final MimeTypeValue APPLICATION_PDF = new MimeTypeValue("application/pdf");
    public static final MimeTypeValue APPLICATION_ZIP = new MimeTypeValue("application/zip");
    public static final MimeTypeValue APPLICATION_GZIP = new MimeTypeValue("application/gzip");
    public static final MimeTypeValue APPLICATION_JAVASCRIPT = new MimeTypeValue("application/javascript");
    public static final MimeTypeValue APPLICATION_MSWORD = new MimeTypeValue("application/msword");
    public static final MimeTypeValue APPLICATION_VND_OPENXML_DOC = new MimeTypeValue("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MimeTypeValue APPLICATION_VND_EXCEL = new MimeTypeValue("application/vnd.ms-excel");
    public static final MimeTypeValue APPLICATION_VND_OPENXML_SHEET = new MimeTypeValue("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MimeTypeValue APPLICATION_RTF = new MimeTypeValue("application/rtf");
    public static final MimeTypeValue APPLICATION_EPUB_ZIP = new MimeTypeValue("application/epub+zip");
    public static final MimeTypeValue APPLICATION_XHTML_XML = new MimeTypeValue("application/xhtml+xml");
    public static final MimeTypeValue APPLICATION_TAR = new MimeTypeValue("application/x-tar");
    public static final MimeTypeValue APPLICATION_X_7Z_COMPRESSED = new MimeTypeValue("application/x-7z-compressed");
    public static final MimeTypeValue APPLICATION_X_RAR = new MimeTypeValue("application/vnd.rar");
    public static final MimeTypeValue APPLICATION_OCTET_STREAM = new MimeTypeValue("application/octet-stream");
    public static final MimeTypeValue APPLICATION_X_SH = new MimeTypeValue("application/x-sh");
    public static final MimeTypeValue APPLICATION_X_HTTPD_PHP = new MimeTypeValue("application/x-httpd-php");
    public static final MimeTypeValue APPLICATION_PROBLEM_JSON = new MimeTypeValue("application/problem+json");
    public static final MimeTypeValue APPLICATION_JSON_PATCH = new MimeTypeValue("application/json-patch+json");
    public static final MimeTypeValue TEXT_PLAIN = new MimeTypeValue("text/plain");
    public static final MimeTypeValue TEXT_HTML = new MimeTypeValue("text/html");
    public static final MimeTypeValue TEXT_CSS = new MimeTypeValue("text/css");
    public static final MimeTypeValue TEXT_CSV = new MimeTypeValue("text/csv");
    public static final MimeTypeValue TEXT_XML = new MimeTypeValue("text/xml");
    public static final MimeTypeValue FONT_WOFF = new MimeTypeValue("font/woff");
    public static final MimeTypeValue FONT_WOFF2 = new MimeTypeValue("font/woff2");
    public static final MimeTypeValue APPLICATION_FONT_TTF = new MimeTypeValue("application/x-font-ttf");
    public static final MimeTypeValue APPLICATION_FONT_OTF = new MimeTypeValue("application/x-font-opentype");
    public static final MimeTypeValue AUDIO_MPEG = new MimeTypeValue("audio/mpeg");
    public static final MimeTypeValue AUDIO_OGG = new MimeTypeValue("audio/ogg");
    public static final MimeTypeValue AUDIO_WAV = new MimeTypeValue("audio/wav");
    public static final MimeTypeValue VIDEO_MP4 = new MimeTypeValue("video/mp4");
    public static final MimeTypeValue VIDEO_WEBM = new MimeTypeValue("video/webm");
    public static final MimeTypeValue VIDEO_OGG = new MimeTypeValue("video/ogg");
    private static final MimeTypeValue NULL_INSTANCE = new MimeTypeValue(null);
    private static final Map<String, MimeTypeValue> INSTANCES = new ConcurrentHashMap<>();

    private MimeTypeValue(@Nullable String value) {
        super(value);
    }

    public static MimeTypeValue of(@Nullable String value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, MimeTypeValue::new);
        }
    }

    @Override
    protected MimeTypeValue createObject(@Nullable String value) {
        return of(value);
    }
}
