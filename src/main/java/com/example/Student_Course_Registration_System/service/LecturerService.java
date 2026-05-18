package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Lecturer;
import com.example.Student_Course_Registration_System.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    // Generate lecturer ID
    public String generateLecturerId() {
        return lecturerRepository.generateId();
    }

    // Add new lecturer
    public void addLecturer(Lecturer lecturer) {
        Lecturer existing = lecturerRepository.findById(lecturer.getLecturerId());
        if (existing != null) {
            System.out.println("Lecturer ID already exists");
            return;
        }
        List<Lecturer> lecturers = lecturerRepository.findAll();
        for (Lecturer l : lecturers) {
            if (l.getEmail().equals(lecturer.getEmail())) {
                System.out.println("Email already exists");
                return;
            }
        }
        lecturerRepository.save(lecturer);
        System.out.println("Lecturer added successfully");
    }

    // Get all lecturers
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    // Get lecturer by ID
    public Lecturer getLecturerById(String lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId);
        if (lecturer == null) {
            System.out.println("Lecturer not found");
        }
        return lecturer;
    }

    // Update lecturer
    public void updateLecturer(Lecturer lecturer) {
        Lecturer existing = lecturerRepository.findById(lecturer.getLecturerId());
        if (existing == null) {
            System.out.println("Lecturer not found");
            return;
        }
        lecturerRepository.update(lecturer);
        System.out.println("Lecturer updated successfully");
    }

    // Delete lecturer
    public void deleteLecturer(String lecturerId) {
        Lecturer existing = lecturerRepository.findById(lecturerId);
        if (existing == null) {
            System.out.println("Lecturer not found");
            return;
        }
        lecturerRepository.delete(lecturerId);
        System.out.println("Lecturer deleted successfully");
    }

    // Search lecturer by name
    public List<Lecturer> searchByName(String name) {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<Lecturer> result = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(lecturer);
            }
        }
        return result;
    }

    // Search lecturer by department
    public List<Lecturer> searchByDepartment(String department) {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<Lecturer> result = new ArrayList<>();
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getDepartment().toLowerCase().contains(department.toLowerCase())) {
                result.add(lecturer);
            }
        }
        return result;
    }

    // Login lecturer
    public Lecturer login(String email, String password) {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        for (Lecturer l : lecturers) {
            if (l.getEmail().equals(email) && l.getPassword().equals(password)) {
                return l;
            }
        }
        return null;
    }

    // Get total lecturer count
    public int getTotalLecturers() {
        return lecturerRepository.findAll().size();
    }
}