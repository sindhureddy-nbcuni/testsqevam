package vamservice.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyParser {

    private Properties properties;

    @SuppressWarnings("unused")
    public Properties parseProperties(String configFile) throws FileNotFoundException, IOException {
        properties = new Properties();
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/PropertyFiles/configuration.properties");
        if (inputStream == null) {
            throw new FileNotFoundException("Property File not found");
        } else {
            properties.load(inputStream);
        }

        return properties;
    }

    @SuppressWarnings("unused")
    public Properties parseEnvProperties(String configFile) throws FileNotFoundException, IOException {
        properties = new Properties();
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/PropertyFiles/environment.properties");
        if (inputStream == null) {
            throw new FileNotFoundException("Property File not found");
        } else {
            properties.load(inputStream);
        }
        return properties;
    }
}
