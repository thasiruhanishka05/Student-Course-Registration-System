package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Admin;
<<<<<<< Updated upstream
import com.example.Student_Course_Registration_System.model.Lecturer;
import com.example.Student_Course_Registration_System.model.Student;
import com.example.Student_Course_Registration_System.repository.AdminRepository;
import com.example.Student_Course_Registration_System.repository.LecturerRepository;
import com.example.Student_Course_Registration_System.repository.StudentRepository;
=======
import com.example.Student_Course_Registration_System.repository.AdminRepository;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

<<<<<<< Updated upstream
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

=======
>>>>>>> Stashed changes
    // Generate admin ID
    public String generateAdminId() {
        return adminRepository.generateId();
    }

<<<<<<< Updated upstream
    // Add new admin - returns error message or null on success
    public String addAdmin(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId());
        if (existing != null) {
            return "Admin ID already exists";
        }
        List<Admin> admins = adminRepository.findAll();
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(admin.getEmail())) {
                return "An admin with this email already exists";
            }
            if (a.getPhone().equals(admin.getPhone())) {
                return "An admin with this phone number already exists";
            }
        }
        // Cross-entity email check
        String crossError = checkCrossEntityEmail(admin.getEmail());
        if (crossError != null) return crossError;
        adminRepository.save(admin);
        return null;
=======
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
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    // Update admin - returns error message or null on success
    public String updateAdmin(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId());
        if (existing == null) {
            return "Admin not found";
        }
        // Check duplicate email (exclude self)
        List<Admin> admins = adminRepository.findAll();
        for (Admin a : admins) {
            if (!a.getAdminId().equals(admin.getAdminId())) {
                if (a.getEmail().equalsIgnoreCase(admin.getEmail())) {
                    return "An admin with this email already exists";
                }
                if (a.getPhone().equals(admin.getPhone())) {
                    return "An admin with this phone number already exists";
                }
            }
        }
        // Cross-entity email check
        String crossError = checkCrossEntityEmail(admin.getEmail());
        if (crossError != null) return crossError;
        adminRepository.update(admin);
        return null;
=======
    // Update admin
    public void updateAdmin(Admin admin) {
        Admin existing = adminRepository.findById(admin.getAdminId());
        if (existing == null) {
            System.out.println("Admin not found");
            return;
        }
        adminRepository.update(admin);
        System.out.println("Admin updated successfully");
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

    // Check if email is used by a student or lecturer
    private String checkCrossEntityEmail(String email) {
        for (Student s : studentRepository.findAll()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return "This email is already used by a student";
            }
        }
        for (Lecturer l : lecturerRepository.findAll()) {
            if (l.getEmail().equalsIgnoreCase(email)) {
                return "This email is already used by a lecturer";
            }
        }
        return null;
    }
=======
>>>>>>> Stashed changes
}