package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminSettingsModel {

    public boolean verifyCurrentPassword(String username, String currentPassword) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT password FROM admin WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                return dbPassword.equals(currentPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String newPassword) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE admin SET password = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}