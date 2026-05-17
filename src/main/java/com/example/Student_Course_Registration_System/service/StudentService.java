package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Student;
import com.example.Student_Course_Registration_System.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Generate student ID
    public String generateStudentId() {
        return studentRepository.generateId();
    }

    // Add new student
    public void addStudent(Student student) {
        Student existing = studentRepository.findById(student.getStudentId());
        if (existing != null) {
            System.out.println("Student ID already exists");
            return;
        }
        List<Student> students = studentRepository.findAll();
        for (Student s : students) {
            if (s.getEmail().equals(student.getEmail())) {
                System.out.println("Email already exists");
                return;
            }
        }
        studentRepository.save(student);
        System.out.println("Student added successfully");
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            System.out.println("Student not found");
        }
        return student;
    }

    // Update student
    public void updateStudent(Student student) {
        Student existing = studentRepository.findById(student.getStudentId());
        if (existing == null) {
            System.out.println("Student not found");
            return;
        }
        studentRepository.update(student);
        System.out.println("Student updated successfully");
    }

    // Delete student
    public void deleteStudent(String studentId) {
        Student existing = studentRepository.findById(studentId);
        if (existing == null) {
            System.out.println("Student not found");
            return;
        }
        studentRepository.delete(studentId);
        System.out.println("Student deleted successfully");
    }

    // Search student by name
    public List<Student> searchByName(String name) {
        List<Student> students = studentRepository.findAll();
        List<Student> result = new java.util.ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    // Get total student count
    public int getTotalStudents() {
        return studentRepository.findAll().size();
    }
}