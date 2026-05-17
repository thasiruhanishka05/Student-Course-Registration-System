package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Lecturer;
import com.example.Student_Course_Registration_System.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Lecturer getLecturerByEmail(String email) {
        return lecturerRepository.findByEmail(email);
    }
}
