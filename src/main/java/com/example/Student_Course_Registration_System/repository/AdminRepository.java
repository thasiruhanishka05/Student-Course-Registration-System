package com.example.Student_Course_Registration_System.repository;

import com.example.Student_Course_Registration_System.model.Admin;
import org.springframework.stereotype.Repository;

<<<<<<< Updated upstream
import org.springframework.core.io.ClassPathResource;
=======
>>>>>>> Stashed changes
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

<<<<<<< Updated upstream
    private String getFilePath() {
        try {
            return new ClassPathResource("data/admins.txt").getFile().getAbsolutePath();
        } catch (IOException e) {
            return "src/main/resources/data/admins.txt";
        }
    }
=======
    private static final String FILE_PATH = "src/main/resources/data/admins.txt";
>>>>>>> Stashed changes

    // Auto generate admin ID
    public String generateId() {
        List<Admin> admins = findAll();
        int max = 0;
        for (Admin a : admins) {
            try {
                int num = Integer.parseInt(a.getAdminId().replace("A", ""));
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("A%03d", max + 1);
    }

    // Save admin to txt file
    public void save(Admin admin) {
<<<<<<< Updated upstream
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), true))) {
=======
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
>>>>>>> Stashed changes
            writer.write(admin.getAdminId() + "," +
                    admin.getName() + "," +
                    admin.getEmail() + "," +
                    admin.getPhone() + "," +
                    admin.getAddress() + "," +
                    admin.getPassword() + "," +
                    admin.getAccessLevel());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving admin: " + e.getMessage());
        }
    }

    // Find all admins from txt file
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
<<<<<<< Updated upstream
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
=======
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
>>>>>>> Stashed changes
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Admin admin = new Admin(
                            parts[1], // name
                            parts[2], // email
                            parts[3], // phone
                            parts[4], // address
                            parts[5], // password
                            parts[0], // adminId
                            Integer.parseInt(parts[6]) // accessLevel
                    );
                    admins.add(admin);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading admins: " + e.getMessage());
        }
        return admins;
    }

    // Find admin by ID
    public Admin findById(String adminId) {
        for (Admin admin : findAll()) {
            if (admin.getAdminId().equals(adminId)) {
                return admin;
            }
        }
        return null;
    }

    // Update admin in txt file
    public void update(Admin updatedAdmin) {
        List<Admin> admins = findAll();
<<<<<<< Updated upstream
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), false))) {
=======
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
>>>>>>> Stashed changes
            for (Admin admin : admins) {
                if (admin.getAdminId().equals(updatedAdmin.getAdminId())) {
                    writer.write(updatedAdmin.getAdminId() + "," +
                            updatedAdmin.getName() + "," +
                            updatedAdmin.getEmail() + "," +
                            updatedAdmin.getPhone() + "," +
                            updatedAdmin.getAddress() + "," +
                            updatedAdmin.getPassword() + "," +
                            updatedAdmin.getAccessLevel());
                } else {
                    writer.write(admin.getAdminId() + "," +
                            admin.getName() + "," +
                            admin.getEmail() + "," +
                            admin.getPhone() + "," +
                            admin.getAddress() + "," +
                            admin.getPassword() + "," +
                            admin.getAccessLevel());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating admin: " + e.getMessage());
        }
    }

<<<<<<< Updated upstream
    // Delete admin from txt file
=======

>>>>>>> Stashed changes
    public void delete(String adminId) {
        List<Admin> admins = findAll();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Admin admin : admins) {
                if (!admin.getAdminId().equals(adminId)) {
                    writer.write(admin.getAdminId() + "," +
                            admin.getName() + "," +
                            admin.getEmail() + "," +
                            admin.getPhone() + "," +
                            admin.getAddress() + "," +
                            admin.getPassword() + "," +
                            admin.getAccessLevel());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting admin: " + e.getMessage());
        }
    }
}