package com.microservice.user.client;

import com.microservice.user.controller.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-course", url = "localhost:8080")
public interface ICourseClient {

    @GetMapping("/api/course/{courseId}")
    CourseDTO findCourseById(@PathVariable Long courseId);
}
