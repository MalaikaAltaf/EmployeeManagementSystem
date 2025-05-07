package controllers;

import models.EmployeeModel;
import views.EmployeeProfileView;

import java.util.Map;

public class EmployeeProfileController {
    private EmployeeProfileView view;
    private EmployeeModel model;
    private int empId;

    public EmployeeProfileController(EmployeeProfileView view, EmployeeModel model, int empId) {
        this.view = view;
        this.model = model;
        this.empId = empId;
        loadProfileData();
    }

    private void loadProfileData() {
        Map<String, Object> data = model.getEmployeeProfile(empId);
        view.nameLabel.setText(data.get("first_name") + " " + data.get("last_name"));
        view.emailLabel.setText((String) data.get("email"));
        view.phoneLabel.setText((String) data.get("phone"));
        view.departmentLabel.setText((String) data.get("department"));
        view.designationLabel.setText((String) data.get("designation"));
        view.dateJoinedLabel.setText(data.get("date_joined").toString());
        view.salaryLabel.setText(data.get("salary").toString());

        byte[] imageData = (byte[]) data.get("profile_pic");
        view.setProfilePic(imageData);
    }
    public void loadEmployeeProfile() {
        loadProfileData();
    }
    
}
