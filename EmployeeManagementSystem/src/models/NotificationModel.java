package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NotificationModel {
    private Connection conn;

    public NotificationModel(Connection conn) {
        this.conn = conn;
    }

    public static class Notification {
        public int id;
        public String type; // "Task" or "Leave"
        public String message;
        public Date dueDate;
        public boolean isRead;

        public Notification(int id, String type, String message, Date dueDate, boolean isRead) {
            this.id = id;
            this.type = type;
            this.message = message;
            this.dueDate = dueDate;
            this.isRead = isRead;
        }
    }

    // Fetch upcoming task deadlines within next 7 days for an employee
    public List<Notification> getUpcomingTaskDeadlines(int empId) {
        List<Notification> notifications = new ArrayList<>();
        try {
            String sql = "SELECT task_id, title, end_date FROM employee_tasks WHERE emp_id = ? AND status != 'Completed' AND end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                Date endDate = rs.getDate("end_date");
                String message = "Task '" + title + "' is due on " + endDate.toString();
                notifications.add(new Notification(taskId, "Task", message, endDate, false));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Fetch pending leave approvals for an employee (assuming employee can see their own leave requests)
    public List<Notification> getPendingLeaveApprovals(int empId) {
        List<Notification> notifications = new ArrayList<>();
        try {
            String sql = "SELECT leave_id, start_date, end_date FROM leave_requests WHERE emp_id = ? AND status = 'Pending'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int leaveId = rs.getInt("leave_id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                String message = "Leave request from " + startDate.toString() + " to " + endDate.toString() + " is pending approval";
                notifications.add(new Notification(leaveId, "Leave", message, startDate, false));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Fetch all notifications (task deadlines + leave approvals)
    public List<Notification> getAllNotifications(int empId) {
        List<Notification> allNotifications = new ArrayList<>();
        allNotifications.addAll(getUpcomingTaskDeadlines(empId));
        allNotifications.addAll(getPendingLeaveApprovals(empId));
        return allNotifications;
    }
}
