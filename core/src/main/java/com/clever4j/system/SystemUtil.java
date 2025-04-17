package com.clever4j.system;

public final class SystemUtil {

    private SystemUtil() {

    }

    public static String getEnvVariable(String name, boolean print, String defaultValue) {
        String value = System.getenv(name);

        value = value != null ? value : defaultValue;

        if (print) {
            System.out.println("%s: " + value);
        }

        return value;
    }
}
