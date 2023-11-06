package com.nashtech.assetmanagement.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertiesFileUtil {
    private static ThreadLocal<Properties> propertiesThreadLocal = new ThreadLocal<>();
    private static final Logger LOGGER = LogManager.getLogger(PropertiesFileUtil.class);

    private PropertiesFileUtil() {
    }

    public static Properties loadPropertiesFromFile(String filePath) throws IOException {
        LOGGER.info("Load Properties file {} at thread {} ", filePath, Thread.currentThread().getId());
        try (InputStream input = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        }
    }

    public static void appendSystemProperties(Properties properties) {
        for (String name : properties.stringPropertyNames()) {
            String value = properties.getProperty(name);
            System.setProperty(name, value);
        }
    }

}
