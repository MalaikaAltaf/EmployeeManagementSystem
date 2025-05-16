-- Create the EMS database
CREATE DATABASE IF NOT EXISTS EMS1;
USE EMS1;

-- Admin Table
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Employee Table
CREATE TABLE employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    department VARCHAR(50),
    designation VARCHAR(50),
    date_joined DATE,
    salary DECIMAL(10, 2),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL, -- Comma added here
    profile_pic LONGBLOB,           -- No comma here (last field)
    theme_preference VARCHAR(10) DEFAULT 'Light'
);


-- Attendance Table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT,
    login_time DATETIME,
    logout_time DATETIME,
    total_hours DECIMAL(5, 2),
    attendance_date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);

-- Leave Requests Table
CREATE TABLE leave_requests (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT,
    start_date DATE,
    end_date DATE,
    reason TEXT,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    request_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);

-- Salary Table
CREATE TABLE salary (
    salary_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT,
    base_salary DECIMAL(10, 2),
    bonuses DECIMAL(10, 2) DEFAULT 0,
    deductions DECIMAL(10, 2) DEFAULT 0,
    total_salary DECIMAL(10, 2),
    payment_date DATE,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);

ALTER TABLE salary ADD COLUMN payslip_generated BOOLEAN DEFAULT FALSE;
ALTER TABLE salary ADD CONSTRAINT unique_salary_per_month UNIQUE (emp_id, payment_date);
-- Employee Tasks Table
CREATE TABLE employee_tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    status ENUM('Pending', 'In Progress', 'Completed') DEFAULT 'Pending',
    start_date DATE,
    end_date DATE,
    performance_rating INT CHECK (performance_rating BETWEEN 1 AND 10),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id)
);



-- Insert dummy admin users
INSERT INTO admin (username, password) VALUES 
('admin1', 'admin123'),
('superadmin', 'rootpass');

-- Insert dummy employee users
INSERT INTO employee (
    first_name, last_name, email, phone, department, designation, date_joined,
    salary, username, password, profile_pic
) VALUES
('Ali', 'Khan', 'ali.khan@example.com', '03121234567', 'HR', 'Manager', '2022-01-15', 75000.00, 'alikhan', 'pass123', LOAD_FILE('C:\\Users\\hp\\Desktop\\bilal.png')),
('Sara', 'Ahmed', 'sara.ahmed@example.com', '03219876543', 'IT', 'Developer', '2023-06-01', 90000.00, 'saraahmed', 'devpass', LOAD_FILE('C:\\Users\\hp\\Desktop\\bilal.png')),
('Bilal', 'Raza', 'bilal.raza@example.com', '03331239876', 'Finance', 'Accountant', '2021-11-20', 65000.00, 'bilalraza', 'accpass', LOAD_FILE('C:\\Users\\hp\\Desktop\\bilal.png'));

INSERT INTO employee_tasks (emp_id, title, description, status, start_date, end_date, performance_rating)
VALUES (1, 'Prepare Monthly Report', 'Compile and analyze HR performance data', 'In Progress', '2025-05-01', '2025-05-10', 8);

CREATE TABLE department (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO department (dept_name) VALUES
('IT'),
('Database'),
('Logistic');