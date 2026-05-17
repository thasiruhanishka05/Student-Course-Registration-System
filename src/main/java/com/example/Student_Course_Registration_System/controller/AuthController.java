package com.example.Student_Course_Registration_System.controller;

import com.example.Student_Course_Registration_System.model.Admin;
import com.example.Student_Course_Registration_System.model.Lecturer;
import com.example.Student_Course_Registration_System.model.Student;
import com.example.Student_Course_Registration_System.service.AdminService;
import com.example.Student_Course_Registration_System.service.LecturerService;
import com.example.Student_Course_Registration_System.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();

        // 1. Admin login (from file)
        Admin admin = adminService.getAdminByEmail(trimmedEmail);
        if (admin != null && admin.getPassword().equals(trimmedPassword)) {
            session.setAttribute("userRole", "ADMIN");
            session.setAttribute("userName", admin.getName());
            session.setAttribute("userId", admin.getAdminId());
            return "redirect:/courses";
        }

        // Hardcoded Admin fallback (updated to match user expectation)
        if ("admin@gmail.com".equalsIgnoreCase(trimmedEmail) && "admin123".equals(trimmedPassword)) {
            session.setAttribute("userRole", "ADMIN");
            session.setAttribute("userName", "Administrator");
            session.setAttribute("userId", "ADMIN001");
            return "redirect:/courses";
        }

        // 2. Lecturer login
        Lecturer lecturer = lecturerService.getLecturerByEmail(trimmedEmail);
        if (lecturer != null && lecturer.getPassword().equals(trimmedPassword)) {
            session.setAttribute("userRole", "LECTURER");
            session.setAttribute("userName", lecturer.getName());
            session.setAttribute("userId", lecturer.getLecturerId());
            return "redirect:/courses";
        }

        // 3. Student login
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(trimmedEmail) && student.getPassword().equals(trimmedPassword)) {
                session.setAttribute("userRole", "STUDENT");
                session.setAttribute("userName", student.getName());
                session.setAttribute("userId", student.getStudentId());
                return "redirect:/courses";
            }
        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/courses";
    }
}
