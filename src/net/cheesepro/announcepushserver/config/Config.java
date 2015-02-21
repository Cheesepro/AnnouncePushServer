package net.cheesepro.announcepushserver.config;

import net.cheesepro.announcepushserver.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Mark on 2015-02-21.
 */
public class Config {

    private Main main;

    Properties prop = new Properties();
    OutputStream output = null;
    InputStream input = null;
    String location = "APS.properties";

    public void set(String key, String value) throws Exception{
        try {
            output = new FileOutputStream(location);
            prop.setProperty(key, value);
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String get(String key) throws Exception{
        try {
            input = new FileInputStream(location);
            prop.load(input);
            return prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop.getProperty(key);
    }

}
