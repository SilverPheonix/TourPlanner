package at.technikum.studentmanagementsystem2.config;

import java.io.IOException;
import java.io.InputStream;

public class Properties_Config {
    private static final String PROPERTIES_FILE = "/at/technikum/studentmanagementsystem2/config.properties";

    public static java.util.Properties loadProperties() throws IOException {
        java.util.Properties props = new java.util.Properties();
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + PROPERTIES_FILE);
            }
            props.load(input);
        }
        return props;
    }
}
