package com.example.Student_Course_Registration_System.controller;

import com.example.Student_Course_Registration_System.model.Student;
import com.example.Student_Course_Registration_System.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("students", studentService.getAllStudents());
        return "student";
    }

    @PostMapping("/students/add")
    public String addStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String enrollmentDate,
            @RequestParam int semester){

        String studentId = studentService.generateStudentId();
        Student student = new Student(name, email, phone, address,
                password, studentId, enrollmentDate, semester);
        student.setStatus("ACTIVE");
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/students/edit/{studentId}")
    public String updateStudent(
            @PathVariable String studentId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String password,
            @RequestParam String enrollmentDate,
            @RequestParam int semester,
            @RequestParam String status) {

        Student existingStudent = studentService.getStudentById(studentId);
        String finalPassword = (password != null && !password.trim().isEmpty()) ? password : existingStudent.getPassword();
        Student student = new Student(name, email, phone, address,
                finalPassword, studentId, enrollmentDate, semester);
        student.setStatus(status);
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{studentId}")
    public String deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
        return "redirect:/students";
    }

    @GetMapping("/students/search")
    public String searchStudent(@RequestParam String name, Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("students", studentService.searchByName(name));
        return "student";
    }
}