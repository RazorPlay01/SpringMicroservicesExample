package com.microservice.course.service;

import com.microservice.course.http.response.CourseResponse;

public interface ICourseService {
    CourseResponse findStudentsAndTeachersByCourse(Long courseId);
}
