package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTaskModel {
    private Connection connection;

    public EmployeeTaskModel(Connection connection) {
        this.connection = connection;
    }

    public boolean addTask(int empId, String title, String description, String status, Date startDate, Date endDate, int performanceRating) {
        String sql = "INSERT INTO employee_tasks (emp_id, title, description, status, start_date, end_date, performance_rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, empId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, status);
            stmt.setDate(5, startDate);
            stmt.setDate(6, endDate);
            stmt.setInt(7, performanceRating);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM employee_tasks WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTask(int taskId, String title, String description, String status, Date startDate, Date endDate, int performanceRating) {
        String sql = "UPDATE employee_tasks SET title = ?, description = ?, status = ?, start_date = ?, end_date = ?, performance_rating = ? WHERE task_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, status);
            stmt.setDate(4, startDate);
            stmt.setDate(5, endDate);
            stmt.setInt(6, performanceRating);
            stmt.setInt(7, taskId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Task> getTasksByEmployee(int empId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM employee_tasks WHERE emp_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("task_id"),
                    rs.getInt("emp_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getInt("performance_rating"),
                    rs.getTimestamp("created_at")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Inner class for Task
    public static class Task {
        public int taskId;
        public int empId;
        public String title;
        public String description;
        public String status;
        public Date startDate;
        public Date endDate;
        public int performanceRating;
        public Timestamp createdAt;

        public Task(int taskId, int empId, String title, String description, String status,
                    Date startDate, Date endDate, int performanceRating, Timestamp createdAt) {
            this.taskId = taskId;
            this.empId = empId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.performanceRating = performanceRating;
            this.createdAt = createdAt;
        }
    }
}
