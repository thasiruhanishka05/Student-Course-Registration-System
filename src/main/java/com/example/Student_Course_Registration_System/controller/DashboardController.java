package com.example.Student_Course_Registration_System.controller;

import com.example.Student_Course_Registration_System.service.CourseService;
import com.example.Student_Course_Registration_System.service.RegistrationService;
import com.example.Student_Course_Registration_System.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));

        model.addAttribute("totalStudents", studentService.getTotalStudents());
        model.addAttribute("totalCourses", courseService.getTotalCourses());
        model.addAttribute("totalRegistrations", registrationService.getAllRegistrations().size());
        model.addAttribute("pendingRegistrations", registrationService.getPendingRegistrations().size());

        return "dashboard";
    }
}
