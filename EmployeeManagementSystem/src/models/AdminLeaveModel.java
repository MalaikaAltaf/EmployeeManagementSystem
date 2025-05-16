package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminLeaveModel {
    private Connection connection;

    public AdminLeaveModel() {
        this.connection = DBConnection.getConnection(); // Use your shared DB connection
    }

    public List<Object[]> getLeavesByStatus(String status) {
        List<Object[]> leaves = new ArrayList<>();
        try {
            String query = "SELECT lr.leave_id, e.first_name, e.last_name, lr.start_date, lr.end_date, lr.reason " +
                           "FROM leave_requests lr JOIN employee e ON lr.emp_id = e.emp_id WHERE lr.status = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                leaves.add(new Object[]{
                        rs.getInt("leave_id"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("reason"),
                        "View"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaves;
    }

    public Object[] getLeaveDetails(int leaveId) {
        try {
            String query = "SELECT lr.leave_id, e.first_name, e.last_name, e.email, lr.start_date, lr.end_date, lr.reason " +
                           "FROM leave_requests lr JOIN employee e ON lr.emp_id = e.emp_id WHERE lr.leave_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, leaveId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{
                        rs.getInt("leave_id"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("reason")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateLeaveStatus(int leaveId, String status) {
        try {
            String query = "UPDATE leave_requests SET status = ? WHERE leave_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, leaveId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}