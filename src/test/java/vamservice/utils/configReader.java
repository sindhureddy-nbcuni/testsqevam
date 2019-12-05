package vamservice.utils;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.Properties;

public class configReader {
    private static Properties configFile;


    static {

        try {
            String path="src/test/resources/PropertyFiles/configuration.properties";
            FileInputStream input=new FileInputStream(path);

            configFile =new Properties();
            configFile.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyName){
        return configFile.getProperty(keyName);

    }

}