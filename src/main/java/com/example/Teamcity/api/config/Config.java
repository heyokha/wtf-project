package com.example.Teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final static String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties("");
    }

    private static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private void loadProperties(String fileName) {
        try(InputStream stream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                System.err.println("File not found " + fileName);
                properties.load(stream);
            }
        } catch (IOException e) {
            System.err.println("Error during file reading " + fileName);
            throw new RuntimeException();
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
