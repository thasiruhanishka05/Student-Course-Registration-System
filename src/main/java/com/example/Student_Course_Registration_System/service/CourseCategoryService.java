package com.example.Student_Course_Registration_System.service;

import com.example.Student_Course_Registration_System.model.CourseCategory;
import com.example.Student_Course_Registration_System.repository.CourseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCategoryService {

    @Autowired
    private CourseCategoryRepository categoryRepository;

    // Add new category
    public void addCategory(CourseCategory category) {
        // Check if category ID already exists
        CourseCategory existing = categoryRepository.findById(category.getCategoryId());
        if (existing != null) {
            System.out.println("Category ID already exists");
            return;
        }
        // Check if category name already exists
        List<CourseCategory> categories = categoryRepository.findAll();
        for (CourseCategory c : categories) {
            if (c.getCategoryName().equalsIgnoreCase(category.getCategoryName())) {
                System.out.println("Category name already exists");
                return;
            }
        }
        categoryRepository.save(category);
        System.out.println("Category added successfully");
    }

    // Get all categories
    public List<CourseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public CourseCategory getCategoryById(String categoryId) {
        CourseCategory category = categoryRepository.findById(categoryId);
        if (category == null) {
            System.out.println("Category not found");
        }
        return category;
    }

    // Update category
    public void updateCategory(CourseCategory category) {
        CourseCategory existing = categoryRepository.findById(category.getCategoryId());
        if (existing == null) {
            System.out.println("Category not found");
            return;
        }
        categoryRepository.update(category);
        System.out.println("Category updated successfully");
    }

    // Delete category
    public void deleteCategory(String categoryId) {
        CourseCategory existing = categoryRepository.findById(categoryId);
        if (existing == null) {
            System.out.println("Category not found");
            return;
        }
        categoryRepository.delete(categoryId);
        System.out.println("Category deleted successfully");
    }

    // Search category by name
    public List<CourseCategory> searchByName(String name) {
        List<CourseCategory> categories = categoryRepository.findAll();
        List<CourseCategory> result = new ArrayList<>();
        for (CourseCategory category : categories) {
            if (category.getCategoryName().toLowerCase().contains(name.toLowerCase())) {
                result.add(category);
            }
        }
        return result;
    }

    // Get total category count
    public int getTotalCategories() {
        return categoryRepository.findAll().size();
    }
}