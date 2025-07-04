package at.technikum.studentmanagementsystem2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    public static void initializeDatabase() {
        try {
            // Load properties
            Properties props = Properties_Config.loadProperties();
            String url = props.getProperty("db.url"); // Database connection URL
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Create the database if it doesn't exist
            createDatabase(url, user, password);

            // Initialize the schema (tables, relations, etc.)
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                initializeSchema(connection);
                System.out.println("Database initialization complete.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the database.", e);
        }
    }



    private static void createDatabase(String url, String user, String password) throws SQLException {
        // Modify the connection URL to connect to the "default" database (e.g., postgres)
        String adminUrl = url.replace("/tour_manager_db", "/postgres");

        try (Connection conn = DriverManager.getConnection(adminUrl, user, password);
             Statement stmt = conn.createStatement()) {
            // Check if the database exists, and create it if it does not exist
            String sql = "SELECT 1 FROM pg_database WHERE datname = 'tour_manager_db'";
            if (!stmt.executeQuery(sql).next()) {
                stmt.executeUpdate("CREATE DATABASE tour_manager_db");
                System.out.println("Database 'tour_manager_db' created successfully.");
            } else {
                System.out.println("Database 'tour_manager_db' already exists.");
            }
        }
    }

    private static void initializeSchema(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // SQL to create schema for tours and tour_logs
            String createToursTable = """
                CREATE TABLE IF NOT EXISTS tours (
                    id UUID PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    description TEXT,
                    "from" VARCHAR(255) NOT NULL,
                    "to" VARCHAR(255) NOT NULL,
                    transport_type VARCHAR(100),
                    distance DOUBLE PRECISION,
                    estimated_time DOUBLE PRECISION,
                    image_url TEXT,
                    popularity INTEGER,
                    child_friendliness DOUBLE PRECISION,
                    startLat DOUBLE PRECISION,
                    startLon DOUBLE PRECISION,
                    endLat DOUBLE PRECISION,
                    endLon DOUBLE PRECISION
                );
            """;

            String createTourLogsTable = """
                CREATE TABLE IF NOT EXISTS tour_logs (
                    id UUID PRIMARY KEY,
                    tour_id UUID NOT NULL,
                    date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    total_distance DOUBLE PRECISION,
                    total_time DOUBLE PRECISION,
                    comment TEXT,
                    rating INTEGER NOT NULL,
                    difficulty VARCHAR(255) NOT NULL,
                    FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE CASCADE
                );
            """;

            // Execute the schema creation SQL
            stmt.executeUpdate(createToursTable);
            stmt.executeUpdate(createTourLogsTable);
            System.out.println("Schema initialized successfully.");
        }
    }
}