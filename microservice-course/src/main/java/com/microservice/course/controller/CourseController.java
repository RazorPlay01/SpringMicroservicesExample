package com.microservice.course.controller;

import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.service.ICourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    ICourseService courseService;

    @GetMapping()
    public ResponseEntity<List<CourseDTO>> getAll() {
        return new ResponseEntity<>(this.courseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.findByID(id), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.save(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody @Valid CourseDTO courseDTO, @PathVariable Long id) {
        return new ResponseEntity<>(courseService.update(courseDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourseById(@PathVariable Long id) {
        courseService.deleteUserById(id);
    }

    @GetMapping("/info/{courseId}")
    public ResponseEntity<?> findAllCourseInfo(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.findStudentsAndTeachersByCourse(courseId));
    }
}
