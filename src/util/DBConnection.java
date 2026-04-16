package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DBConnection {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/payment_gateway";
    private static final Properties FILE_PROPERTIES = loadProperties();

    public static Connection getConnection() {

        try {
            Connection con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
            return con;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to connect to the database. Configure db.properties or DB_URL/DB_USER/DB_PASSWORD first.", e);
        }
    }

    private static Properties loadProperties() {

        Properties properties = new Properties();
        Path configPath = Paths.get("db.properties");

        if (Files.notExists(configPath)) {
            return properties;
        }

        try (InputStream inputStream = Files.newInputStream(configPath)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read db.properties", e);
        }
    }

    private static String getUrl() {
        return getSetting(URL_KEY, "DB_URL", DEFAULT_URL);
    }

    private static String getUser() {
        return getRequiredSetting(USER_KEY, "DB_USER");
    }

    private static String getPassword() {
        return getRequiredSetting(PASSWORD_KEY, "DB_PASSWORD");
    }

    private static String getRequiredSetting(String propertyKey, String envKey) {

        String value = getSetting(propertyKey, envKey, null);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing database setting: " + propertyKey);
        }

        return value;
    }

    private static String getSetting(String propertyKey, String envKey, String defaultValue) {

        String value = FILE_PROPERTIES.getProperty(propertyKey);

        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(envKey);
        }

        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }

        return value.trim();
    }
}
