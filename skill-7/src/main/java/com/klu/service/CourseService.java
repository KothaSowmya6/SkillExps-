package com.klu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.model.Course;
import com.klu.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    public Course addCourse(Course course) {
        return repository.save(course);
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return repository.findById(id);
    }

    public Course updateCourse(Long id, Course course) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(course.getTitle());
            existing.setDuration(course.getDuration());
            existing.setFee(course.getFee());
            return repository.save(existing);
        }).orElse(null);
    }

    public boolean deleteCourse(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Course> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}
