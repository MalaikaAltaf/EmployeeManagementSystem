package models;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class SalaryModel {

    // 1. Fetch all salary records for an employee
    public ArrayList<String[]> fetchSalaryByEmpId(int empId) {
        ArrayList<String[]> salaryList = new ArrayList<>();
        String query = "SELECT base_salary, bonuses, deductions, total_salary, payment_date " +
                       "FROM salary WHERE emp_id = ? ORDER BY payment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

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

    // 2. Fetch salary records in a date range
    public ArrayList<String[]> fetchSalaryByDateRange(int empId, String startDate, String endDate) {
        ArrayList<String[]> salaryList = new ArrayList<>();
        String query = "SELECT base_salary, bonuses, deductions, total_salary, payment_date " +
                       "FROM salary WHERE emp_id = ? AND payment_date BETWEEN ? AND ? ORDER BY payment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
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

    // 3. Generate a payslip as a .txt file
    public boolean generatePayslip(int empId, String paymentDate) {
        String[] salaryData = null;
        String query = "SELECT base_salary, bonuses, deductions, total_salary FROM salary WHERE emp_id = ? AND payment_date = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empId);
            stmt.setString(2, paymentDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                salaryData = new String[] {
                    rs.getString("base_salary"),
                    rs.getString("bonuses"),
                    rs.getString("deductions"),
                    rs.getString("total_salary")
                };
            }

            if (salaryData == null) return false;

            // Directory chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select directory to save payslip");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return false;
            }

            String saveDir = fileChooser.getSelectedFile().getAbsolutePath();
            String fileName = "Payslip_" + empId + "_" + paymentDate.replaceAll("-", "") + ".txt";
            String filePath = saveDir + "/" + fileName;

            // Write to .txt file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("----------- Salary Payslip -----------\n");
                writer.write("Employee ID : " + empId + "\n");
                writer.write("Payment Date: " + paymentDate + "\n");
                writer.write("--------------------------------------\n");
                writer.write("Base Salary : " + salaryData[0] + "\n");
                writer.write("Bonuses     : " + salaryData[1] + "\n");
                writer.write("Deductions  : " + salaryData[2] + "\n");
                writer.write("Total Salary: " + salaryData[3] + "\n");
                writer.write("--------------------------------------\n");
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Fetch salary records filtered by status
    public ArrayList<String[]> fetchSalaryByStatus(int empId, String status) {
        ArrayList<String[]> salaryList = new ArrayList<>();
        String query = "SELECT base_salary, bonuses, deductions, total_salary, payment_date " +
                       "FROM salary WHERE emp_id = ? AND status = ? ORDER BY payment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, empId);
            stmt.setString(2, status);
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
