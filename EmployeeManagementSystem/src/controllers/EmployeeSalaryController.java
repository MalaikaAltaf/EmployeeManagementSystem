package controllers;

import models.SalaryModel;
import views.EmployeeSalaryView;

public class EmployeeSalaryController {
    private EmployeeSalaryView view;
    private SalaryModel model;
    private int empId;

    public EmployeeSalaryController(EmployeeSalaryView view, SalaryModel model, int empId) {
        this.view = view;
        this.model = model;
        this.empId = empId;
    }

    public void loadSalaryData() {
        var data = model.fetchSalaryByEmpId(empId);
        view.updateSalaryTable(data);
    }
}
