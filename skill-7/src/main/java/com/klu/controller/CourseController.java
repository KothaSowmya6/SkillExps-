package com.klu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.klu.model.Course;
import com.klu.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {

        if(course.getTitle()==null || course.getFee()==null) {
            return ResponseEntity.badRequest().body("Invalid Course Data");
        }

        Course saved=service.addCourse(course);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(service.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {

        Optional<Course> course = service.getCourseById(id);

        if(course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @RequestBody Course course) {

        Course updated=service.updateCourse(id,course);

        if(updated==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course Not Found");
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {

        if(service.deleteCourse(id)) {
            return ResponseEntity.ok("Course Deleted Successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Course Not Found");
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchCourse(@PathVariable String title) {

        List<Course> list=service.searchByTitle(title);

        if(list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Courses Found");
        }

        return ResponseEntity.ok(list);
    }
}
