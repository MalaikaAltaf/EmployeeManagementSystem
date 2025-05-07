package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveModel {

    public boolean submitLeaveRequest(int empId, String startDate, String endDate, String reason) {
        String query = "INSERT INTO leave_requests (emp_id, start_date, end_date, reason) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            stmt.setString(4, reason);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getLeaveRequests(int empId) {
        List<String[]> requests = new ArrayList<>();
        String query = "SELECT start_date, end_date, reason, status, request_date FROM leave_requests WHERE emp_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(new String[]{
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getString("request_date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }
}
