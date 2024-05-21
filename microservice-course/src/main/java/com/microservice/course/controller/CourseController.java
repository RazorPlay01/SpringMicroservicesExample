package com.microservice.course.controller;

import com.microservice.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    ICourseService courseService;


    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> findAllCourseInfo(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.findStudentsAndTeachersByCourse(courseId));
    }
}
