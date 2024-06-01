package com.microservice.course.service;

import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.http.response.CourseResponse;

import java.util.List;

public interface ICourseService {
    List<CourseDTO> findAll();

    CourseDTO findByID(Long id);

    CourseDTO save(CourseDTO courseDTO);

    CourseDTO update(CourseDTO courseDTO, Long id);

    void deleteUserById(Long id);

    CourseResponse findStudentsAndTeachersByCourse(Long courseId);
}
