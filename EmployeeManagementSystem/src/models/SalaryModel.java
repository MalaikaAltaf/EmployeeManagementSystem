package models;

import java.sql.*;
import java.util.ArrayList;

public class SalaryModel {

    public ArrayList<String[]> fetchSalaryByEmpId(int empId) {
        ArrayList<String[]> salaryList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT base_salary, bonuses, deductions, total_salary, payment_date FROM salary WHERE emp_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] row = {
                    rs.getString("base_salary"),
                    rs.getString("bonuses"),
                    rs.getString("deductions"),
                    rs.getString("total_salary"),
                    rs.getString("payment_date")
                };
                salaryList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryList;
    }
}
