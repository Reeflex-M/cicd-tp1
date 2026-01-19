package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    public PricingConfig load() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find app.properties");
            }
            props.load(input);
            
            double vat = Double.parseDouble(required(props, "vatRate"));
            double threshold = Double.parseDouble(required(props, "freeShippingThreshold"));
            
            return new PricingConfig(vat, threshold);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration", ex);
        }
    }

    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required configuration key: " + key);
        }
        return value;
    }
}