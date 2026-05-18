package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Admin;
import com.example.Student_Course_Registration_System.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Generate admin ID
    public String generateAdminId() {
        return adminRepository.generateId();
    }

    // Add new admin
    public void addAdmin(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId());
        if (existing != null) {
            System.out.println("Admin ID already exists");
            return;
        }
        List<Admin> admins = adminRepository.findAll();
        for (Admin a : admins) {
            if (a.getEmail().equals(admin.getEmail())) {
                System.out.println("Email already exists");
                return;
            }
        }
        adminRepository.save(admin);
        System.out.println("Admin added successfully");
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID
    public Admin getAdminById(String adminId) {
        Admin admin = adminRepository.findById(adminId);
        if (admin == null) {
            System.out.println("Admin not found");
        }
        return admin;
    }

    // Update admin
    public void updateAdmin(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId());
        if (existing == null) {
            System.out.println("Admin not found");
            return;
        }
        adminRepository.update(admin);
        System.out.println("Admin updated successfully");
    }

    // Delete admin
    public void deleteAdmin(String adminId) {
        Admin existing = adminRepository.findById(adminId);
        if (existing == null) {
            System.out.println("Admin not found");
            return;
        }
        if (adminRepository.findAll().size() == 1) {
            System.out.println("Cannot delete last admin");
            return;
        }
        adminRepository.delete(adminId);
        System.out.println("Admin deleted successfully");
    }

    // Login admin - checks both email and password
    public Admin login(String email, String password) {
        List<Admin> admins = adminRepository.findAll();
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email) &&
                    admin.getPassword().equals(password)) {
                return admin;
            }
        }
        System.out.println("Invalid email or password");
        return null;
    }

    // Search admin by name
    public List<Admin> searchByName(String name) {
        List<Admin> admins = adminRepository.findAll();
        List<Admin> result = new ArrayList<>();
        for (Admin admin : admins) {
            if (admin.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(admin);
            }
        }
        return result;
    }

    // Get total admin count
    public int getTotalAdmins() {
        return adminRepository.findAll().size();
    }
}