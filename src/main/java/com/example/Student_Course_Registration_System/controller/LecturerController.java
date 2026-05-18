package com.example.Student_Course_Registration_System.controller;

import com.example.Student_Course_Registration_System.model.Lecturer;
import com.example.Student_Course_Registration_System.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    // Show all lecturer
    @GetMapping("/lecturers")
    public String getAllLecturers(Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("lecturers", lecturerService.getAllLecturers());
        return "lecturer";
    }

    // Add new lecturer - ID auto generated
    @PostMapping("/lecturers/add")
    public String addLecturer(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String department,
            @RequestParam String specialization) {

        String lecturerId = lecturerService.generateLecturerId();
        Lecturer lecturer = new Lecturer(
                name, email, phone, address, password,
                lecturerId, department, specialization
        );
        lecturerService.addLecturer(lecturer);
        return "redirect:/lecturers";
    }

    // Update lecturer
    @PostMapping("/lecturers/edit/{lecturerId}")
    public String updateLecturer(
            @PathVariable String lecturerId,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(required = false) String password,
            @RequestParam String department,
            @RequestParam String specialization) {

        Lecturer existingLecturer = lecturerService.getLecturerById(lecturerId);
        String finalPassword = (password != null && !password.trim().isEmpty()) ? password : existingLecturer.getPassword();
        Lecturer lecturer = new Lecturer(
                name, email, phone, address, finalPassword,
                lecturerId, department, specialization
        );
        lecturerService.updateLecturer(lecturer);
        return "redirect:/lecturers";
    }

    // Delete lecturer
    @GetMapping("/lecturers/delete/{lecturerId}")
    public String deleteLecturer(@PathVariable String lecturerId) {
        lecturerService.deleteLecturer(lecturerId);
        return "redirect:/lecturers";
    }

    // Search lecturer
    @GetMapping("/lecturers/search")
    public String searchLecturer(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            Model model, HttpSession session) {
        model.addAttribute("userRole", session.getAttribute("userRole"));
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("userId", session.getAttribute("userId"));
        if (name != null && !name.isEmpty()) {
            model.addAttribute("lecturers", lecturerService.searchByName(name));
        } else if (department != null && !department.isEmpty()) {
            model.addAttribute("lecturers", lecturerService.searchByDepartment(department));
        } else {
            model.addAttribute("lecturers", lecturerService.getAllLecturers());
        }
        return "lecturer";
    }
}