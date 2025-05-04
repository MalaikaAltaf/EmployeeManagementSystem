package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems1";
    private static final String USER = "root";         // replace with your MySQL username
    private static final String PASSWORD = "root1122"; // replace with your MySQL password

    private static Connection conn = null;

    // Static method to return a connection
    public static Connection getConnection() {
        System.out.println("🔍 Attempting to get database connection...");
        
        if (conn == null) {
            try {
                System.out.println("📦 Loading MySQL JDBC driver...");
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
                System.out.println("🔗 Connecting to database with URL: " + URL);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to EMS database.");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("❌ Database connection failed: " + e.getMessage());
                System.out.println("🔁 Check if MySQL is running, and verify your username/password.");
            }
        } else {
            System.out.println("ℹ️ Returning existing database connection.");
        }

        return conn;
    }

    // Optional method to close connection (not always needed if using only one connection)
    public static void closeConnection() {
        System.out.println("🔄 Attempting to close the database connection...");
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔌 Connection closed successfully.");
            } else {
                System.out.println("⚠️ Connection is already closed or was never established.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing connection: " + e.getMessage());
        }
    }
}
