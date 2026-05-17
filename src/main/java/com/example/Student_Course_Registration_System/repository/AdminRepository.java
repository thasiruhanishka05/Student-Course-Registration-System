package com.example.Student_Course_Registration_System.repository;

import com.example.Student_Course_Registration_System.model.Admin;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    private static final String FILE_PATH = "src/main/resources/data/admins.txt";

    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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

    public Admin findByEmail(String email) {
        for (Admin admin : findAll()) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                return admin;
            }
        }
        return null;
    }
}
