package views;    

import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {
private JLabel titleLabel;
private JLabel statusLabel;
private JLabel ratingLabel;
private JTextArea descriptionArea;
private JButton editButton;
private JButton deleteButton;


public interface EditListener {
    void onEdit();
}

public interface DeleteListener {
    void onDelete();
}

private EditListener editListener;
private DeleteListener deleteListener;

public TaskPanel(String title, String description, String status, int rating) {
    setLayout(new BorderLayout(8, 4));
    setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(60, 70, 85), 1, true),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    ));
    setBackground(new Color(52, 60, 72)); // Softer dark shade

    titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    titleLabel.setForeground(new Color(0x29B6F6)); // Light Blue accent

    statusLabel = new JLabel("Status: " + status);
    statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    statusLabel.setForeground(getStatusColor(status));

    ratingLabel = new JLabel("Rating: " + rating + "/10");
    ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    ratingLabel.setForeground(new Color(0xCE93D8)); // Light purple

    descriptionArea = new JTextArea(description);
    descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    descriptionArea.setForeground(new Color(220, 220, 220));
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setEditable(false);
    descriptionArea.setOpaque(false);
    descriptionArea.setBorder(null);
    descriptionArea.setRows(2);
    descriptionArea.setPreferredSize(new Dimension(100, 40));

    // Edit Button
    editButton = new JButton("Edit");
    editButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
    editButton.setFocusPainted(false);
    editButton.setBackground(new Color(70, 130, 180));
    editButton.setForeground(Color.WHITE);
    editButton.setPreferredSize(new Dimension(60, 30));
    editButton.addActionListener(e -> {
        if (editListener != null) {
            editListener.onEdit();
        }
    });

    // Delete Button
    deleteButton = new JButton("Delete");
    deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
    deleteButton.setFocusPainted(false);
    deleteButton.setBackground(new Color(220, 53, 69)); // Soft red
    deleteButton.setForeground(Color.WHITE);
    deleteButton.setPreferredSize(new Dimension(75, 30));
    deleteButton.addActionListener(e -> {
        if (deleteListener != null) {
            deleteListener.onDelete();
        }
    });

    // Top panel for labels and buttons
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setOpaque(false);
    topPanel.add(titleLabel, BorderLayout.WEST);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(statusLabel);
    buttonPanel.add(ratingLabel);
    buttonPanel.add(editButton);
    buttonPanel.add(deleteButton);

    topPanel.add(buttonPanel, BorderLayout.EAST);

    add(topPanel, BorderLayout.NORTH);
    add(descriptionArea, BorderLayout.CENTER);

    setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
    setPreferredSize(new Dimension(600, 80));

    // Optional hover effect
    addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            setBackground(new Color(60, 68, 80));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            setBackground(new Color(52, 60, 72));
        }
    });
}

public void setEditListener(EditListener listener) {
    this.editListener = listener;
}

public void setDeleteListener(DeleteListener listener) {
    this.deleteListener = listener;
}

private Color getStatusColor(String status) {
    switch (status.toLowerCase()) {
        case "completed":
            return new Color(129, 199, 132); // Soft green
        case "in progress":
            return new Color(255, 213, 79);  // Soft amber
        case "pending":
            return new Color(239, 83, 80);   // Soft red
        default:
            return new Color(158, 158, 158); // Neutral gray
    }
}


}
