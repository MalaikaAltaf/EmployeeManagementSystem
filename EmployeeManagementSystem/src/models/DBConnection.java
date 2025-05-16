package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems1";
    private static final String USER = "root";         // replace with your MySQL username
    private static final String PASSWORD = "root1122"; // replace with your MySQL password

    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to EMS database.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            System.out.println("🔁 Check if MySQL is running, and verify your username/password.");
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to get connection: " + e.getMessage());
        }
        return connection;
    }
}
