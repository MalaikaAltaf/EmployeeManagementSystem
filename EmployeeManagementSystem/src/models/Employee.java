package models;

public class Employee {
    private int empId;
    private String firstName, lastName, email, phone, department, designation, username, password;
    private String dateJoined;
    private double salary;
    private byte[] profilePic;

    // Constructor (excluding empId because it's auto-incremented)
    public Employee(String firstName, String lastName, String email, String phone,
                    String department, String designation, String dateJoined,
                    double salary, String username, String password, byte[] profilePic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.dateJoined = dateJoined;
        this.salary = salary;
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
    }

    // Constructor with empId (optional use)
    public Employee(int empId, String firstName, String lastName, String email, String phone,
                    String department, String designation, String dateJoined,
                    double salary, String username, String password, byte[] profilePic) {
        this(firstName, lastName, email, phone, department, designation, dateJoined, salary, username, password, profilePic);
        this.empId = empId;
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}