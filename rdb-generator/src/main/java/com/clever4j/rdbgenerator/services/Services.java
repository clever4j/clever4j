package com.clever4j.rdbgenerator.services;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Configuration;

@AllNonnullByDefault
public final class Services {

    private static Configuration configuration;

    public static void setConfiguration(Configuration configuration) {
        Services.configuration = configuration;
    }

    public static Configuration configuration() {
        return configuration;
    }
}