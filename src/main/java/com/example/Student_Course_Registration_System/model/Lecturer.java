package com.example.Student_Course_Registration_System.model;

import com.example.Student_Course_Registration_System.enums.Role;

public class Lecturer extends Person {

    private String lecturerId;
    private String department;
    private String specialization;
    private double salary;

    public Lecturer(String name, String email, String phone, String address, String password, String lecturerId, String department, String specialization, double salary) {
        super(name, email, phone, address, password, Role.LECTURER);
        this.lecturerId = lecturerId;
        this.department = department;
        this.specialization = specialization;
        this.salary = salary;
    }

    public String getLecturerId() { return lecturerId; }
    public String getDepartment() { return department; }
    public String getSpecialization() { return specialization; }

    // Setters only for changeable variables
    public void setDepartment(String department) { this.department = department; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public void updateSalary(double newSalary) {
        if (newSalary > 0) {
            this.salary = newSalary;
            System.out.println("Salary updated successfully");
        } else {
            System.out.println("Invalid salary amount");
        }
    }

    // Update profile method
    public void updateProfile(String phone, String address) {
        setPhone(phone);
        setAddress(address);
        System.out.println("Profile updated successfully");
    }

    // Override getDetails (salary never printed)
    @Override
    public void getDetails() {
        System.out.println("Lecturer ID: " + lecturerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Department: " + department);
        System.out.println("Specialization: " + specialization);
    }
}