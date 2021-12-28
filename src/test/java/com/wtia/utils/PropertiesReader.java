package com.wtia.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Properties prop = new Properties();
    private InputStream inputStream = null;

    public PropertiesReader() {
        try {
            String env = System.getProperty("env", "test");
            String propertiesFilePath = String.format("%s/src/test/resources/%s.properties",
                    System.getProperty("user.dir"), env);
            inputStream = new FileInputStream(propertiesFilePath);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        return prop.getProperty("base_url");
    }

}
