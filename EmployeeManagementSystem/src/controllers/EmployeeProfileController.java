package controllers;

import views.EmployeeProfileView;
import models.EmployeeModel;
import java.awt.Image;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class EmployeeProfileController {
    private EmployeeProfileView view;
    private EmployeeModel model;
    private int empId;
    private JFrame profileFrame;

    public EmployeeProfileController(EmployeeProfileView profileView, int empId) {
        view = profileView;
        model = new EmployeeModel();
        this.empId = empId;
        // Removed initial loadProfile call to avoid premature loading
        setupListeners();
    }

    public JPanel getProfilePanel() {
        return view;
    }

    public void loadProfile() {
        System.out.println("loadProfile() called with empId: " + empId);
        view.refreshData();
        view.revalidate();
        view.repaint();
    }

    public void openProfileWindow() {
        System.out.println("openProfileWindow() called");
        if (profileFrame == null) {
            System.out.println("Creating new JFrame for profile");
            profileFrame = new JFrame("Employee Profile");
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.setSize(400, 600);
            profileFrame.setLocationRelativeTo(null);
            profileFrame.add(view);
            profileFrame.pack();
        }
        loadProfile();
        profileFrame.setVisible(true);
        System.out.println("Profile window set visible");
    }

    private void setupListeners() {
        view.uploadPicBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int opt = chooser.showOpenDialog(view);
            if (opt == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    byte[] imageBytes = readImageFile(file);
                    model.saveProfilePicture(empId, imageBytes);
                    ImageIcon icon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    view.profilePicLabel.setIcon(icon);
                    JOptionPane.showMessageDialog(view, "Profile picture updated successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Failed to load image.");
                }
            }
        });
    }

    private byte[] readImageFile(File file) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(file)) {
            byte[] buf = new byte[1024];
            int read;
            while ((read = fis.read(buf)) != -1) {
                bos.write(buf, 0, read);
            }
            return bos.toByteArray();
        }
    }
}
