package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllNonnullByDefault
public final class DatabaseTypeMapper {

    private static final Map<String, String> MAPPING = new HashMap<>();

    static {
        // Typy tekstowe
        MAPPING.put("uuid", UUID.class.getCanonicalName());
        MAPPING.put("varchar", String.class.getCanonicalName());
        MAPPING.put("text", String.class.getCanonicalName());
        MAPPING.put("char", String.class.getCanonicalName());
        MAPPING.put("bpchar", String.class.getCanonicalName()); // Char(n)

        // Typy liczbowe
        MAPPING.put("int2", Short.class.getCanonicalName()); // Smallint
        MAPPING.put("int4", Integer.class.getCanonicalName()); // Integer
        MAPPING.put("int8", Long.class.getCanonicalName()); // Bigint
        MAPPING.put("float4", Float.class.getCanonicalName()); // Real
        MAPPING.put("float8", Double.class.getCanonicalName()); // Double precision
        MAPPING.put("numeric", BigDecimal.class.getCanonicalName()); // Numeric
        MAPPING.put("decimal", BigDecimal.class.getCanonicalName()); // Alias for Numeric

        // Typy logiczne
        MAPPING.put("bool", Boolean.class.getCanonicalName()); // Boolean

        // Typy czasowe
        MAPPING.put("timestamp", LocalDateTime.class.getCanonicalName()); // Timestamp without time zone
        MAPPING.put("timestamptz", LocalDateTime.class.getCanonicalName()); // Timestamp with time zone
        MAPPING.put("date", LocalDate.class.getCanonicalName()); // Date
        MAPPING.put("time", LocalTime.class.getCanonicalName()); // Time without time zone
        MAPPING.put("timetz", LocalTime.class.getCanonicalName()); // Time with time zone

        // Typy binarne
        MAPPING.put("bytea", byte[].class.getCanonicalName()); // Byte array

        // Typy geoprzestrzenne
        MAPPING.put("point", "org.postgis.Point");
        MAPPING.put("lseg", "org.postgis.LineString");
        MAPPING.put("box", "org.postgis.Polygon");
        MAPPING.put("path", "org.postgis.Path");
        MAPPING.put("polygon", "org.postgis.Polygon");
        MAPPING.put("circle", "org.postgis.Circle");

        // Typy specjalne
        MAPPING.put("json", String.class.getCanonicalName()); // JSON jako String
        MAPPING.put("jsonb", String.class.getCanonicalName()); // JSONB jako String
        MAPPING.put("xml", String.class.getCanonicalName()); // XML jako String

        // Typy tablicowe
        MAPPING.put("_int4", "java.util.List<java.lang.Integer>"); // Array of Integer
        MAPPING.put("_varchar", "java.util.List<java.lang.String>"); // Array of String
        MAPPING.put("_text", "java.util.List<java.lang.String>"); // Array of Text
    }

    public String map(String databaseType) {
        if (!MAPPING.containsKey(databaseType)) {
            return String.class.getCanonicalName();
        } else {
            return MAPPING.get(databaseType);
        }
    }
}
