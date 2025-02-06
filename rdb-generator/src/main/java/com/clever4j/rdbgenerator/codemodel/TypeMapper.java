package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllNonnullByDefault
public final class TypeMapper {

    private static final Map<String, Class<?>> MAPPING = new HashMap<>();

    static {
        // Typy tekstowe
        MAPPING.put("uuid", UUID.class);
        MAPPING.put("varchar", String.class);
        MAPPING.put("text", String.class);
        MAPPING.put("char", String.class);
        MAPPING.put("bpchar", String.class); // Char(n)

        // Typy liczbowe
        MAPPING.put("int2", Short.class); // Smallint
        MAPPING.put("int4", Integer.class); // Integer
        MAPPING.put("int8", Long.class); // Bigint
        MAPPING.put("float4", Float.class); // Real
        MAPPING.put("float8", Double.class); // Double precision
        MAPPING.put("numeric", BigDecimal.class); // Numeric
        MAPPING.put("decimal", BigDecimal.class); // Alias for Numeric

        // Typy logiczne
        MAPPING.put("bool", Boolean.class); // Boolean

        // Typy czasowe
        MAPPING.put("timestamp", LocalDateTime.class); // Timestamp without time zone
        MAPPING.put("timestamptz", LocalDateTime.class); // Timestamp with time zone
        MAPPING.put("date", LocalDate.class); // Date
        MAPPING.put("time", LocalTime.class); // Time without time zone
        MAPPING.put("timetz", LocalTime.class); // Time with time zone

        // Typy binarne
        MAPPING.put("bytea", byte[].class); // Byte array

        // Typy geoprzestrzenne
        // MAPPING.put("point", "org.postgis.Point");
        // MAPPING.put("lseg", "org.postgis.LineString");
        // MAPPING.put("box", "org.postgis.Polygon");
        // MAPPING.put("path", "org.postgis.Path");
        // MAPPING.put("polygon", "org.postgis.Polygon");
        // MAPPING.put("circle", "org.postgis.Circle");

        // Typy specjalne
        MAPPING.put("json", String.class); // JSON jako String
        MAPPING.put("jsonb", String.class); // JSONB jako String
        MAPPING.put("xml", String.class); // XML jako String

        // Typy tablicowe
        // MAPPING.put("_int4", "java.util.List<java.lang.Integer>"); // Array of Integer
        // MAPPING.put("_varchar", "java.util.List<java.lang.String>"); // Array of String
        // MAPPING.put("_text", "java.util.List<java.lang.String>"); // Array of Text
    }

    public Class<?> map(String databaseType) {
        return MAPPING.getOrDefault(databaseType, String.class);
    }
}
