package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems1";
    private static final String USER = "root";         // replace with your MySQL username
    private static final String PASSWORD = "root1122"; // replace with your MySQL password

    // Static method to return a new connection each time
    public static Connection getConnection() {
        System.out.println("🔍 Attempting to get database connection...");
        try {
            System.out.println("📦 Loading MySQL JDBC driver...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            System.out.println("🔗 Connecting to database with URL: " + URL);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to EMS database.");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            System.out.println("🔁 Check if MySQL is running, and verify your username/password.");
        }
        return null;
    }
}