package com.microservice.course.service;

import com.microservice.course.client.IUserClient;
import com.microservice.course.controller.dto.StudentDTO;
import com.microservice.course.controller.dto.TeacherDTO;
import com.microservice.course.http.response.CourseResponse;
import com.microservice.course.persistence.entity.CourseEntity;
import com.microservice.course.persistence.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    ICourseRepository repository;

    @Autowired
    IUserClient userClient;

    @Override
    public CourseResponse findStudentsAndTeachersByCourse(Long courseId) {
        CourseEntity course = repository.findById(courseId).orElseThrow();

        List<StudentDTO> studentDTOList = userClient.findAllStudentByCourse(course.getId());
        List<TeacherDTO> teacherDTOList = userClient.findAllTeacherByCourse(course.getId());

        return CourseResponse.builder()
                .courseName(course.getName())
                .studentDTOList(studentDTOList)
                .teachersDTOList(teacherDTOList)
                .build();
    }
}
