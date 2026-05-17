package com.example.Student_Course_Registration_System.repository;

import com.example.Student_Course_Registration_System.model.Lecturer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LecturerRepository {

    private static final String FILE_PATH = "src/main/resources/data/lecturers.txt";

    public List<Lecturer> findAll() {
        List<Lecturer> lecturers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    Lecturer lecturer = new Lecturer(
                            parts[1], // name
                            parts[2], // email
                            parts[3], // phone
                            parts[4], // address
                            parts[5], // password
                            parts[0], // lecturerId
                            parts[6], // department
                            parts[7]  // specialization
                    );
                    lecturers.add(lecturer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading lecturers: " + e.getMessage());
        }
        return lecturers;
    }

    public Lecturer findByEmail(String email) {
        for (Lecturer lecturer : findAll()) {
            if (lecturer.getEmail().equalsIgnoreCase(email)) {
                return lecturer;
            }
        }
        return null;
    }
}
