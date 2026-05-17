package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.Course;
import com.example.Student_Course_Registration_System.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Add new course
    public void addCourse(Course course) {
        // Check if course ID already exists
        Course existing = courseRepository.findById(course.getCourseId());
        if (existing != null) {
            System.out.println("Course ID already exists");
            return;
        }
        // Check if course name already exists
        List<Course> courses = courseRepository.findAll();
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(course.getCourseName())) {
                System.out.println("Course name already exists");
                return;
            }
        }
        courseRepository.save(course);
        System.out.println("Course added successfully");
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Course getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId);
        if (course == null) {
            System.out.println("Course not found");
        }
        return course;
    }

    // Update course
    public void updateCourse(Course course) {
        Course existing = courseRepository.findById(course.getCourseId());
        if (existing == null) {
            System.out.println("Course not found");
            return;
        }
        courseRepository.update(course);
        System.out.println("Course updated successfully");
    }

    // Delete course
    public void deleteCourse(String courseId) {
        Course existing = courseRepository.findById(courseId);
        if (existing == null) {
            System.out.println("Course not found");
            return;
        }
        courseRepository.delete(courseId);
        System.out.println("Course deleted successfully");
    }

    // Get all available courses
    public List<Course> getAvailableCourses() {
        List<Course> courses = courseRepository.findAll();
        List<Course> availableCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isAvailable()) {
                availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    // Search course by name
    public List<Course> searchByName(String name) {
        List<Course> courses = courseRepository.findAll();
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseName().toLowerCase().contains(name.toLowerCase())) {
                result.add(course);
            }
        }
        return result;
    }

    // Search course by category
    public List<Course> searchByCategory(String categoryId) {
        List<Course> courses = courseRepository.findAll();
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCategory().getCategoryId().equals(categoryId)) {
                result.add(course);
            }
        }
        return result;
    }

    // Get total course count
    public int getTotalCourses() {
        return courseRepository.findAll().size();
    }
}