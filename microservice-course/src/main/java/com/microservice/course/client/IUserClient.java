package com.microservice.course.client;

import com.microservice.course.controller.dto.StudentDTO;
import com.microservice.course.controller.dto.TeacherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-user", url = "localhost:8080")
public interface IUserClient {

    @GetMapping("/api/user/student/{courseId}")
    List<StudentDTO> findAllStudentByCourse(@PathVariable Long courseId);

    @GetMapping("/api/user/teacher/{courseId}")
    List<TeacherDTO> findAllTeacherByCourse(@PathVariable Long courseId);
}
