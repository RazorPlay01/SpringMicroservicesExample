package com.microservice.course;

import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.controller.dto.StudentDTO;
import com.microservice.course.controller.dto.TeacherDTO;
import com.microservice.course.http.response.CourseResponse;
import com.microservice.course.persistence.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public class DataProvider {
    public static List<CourseEntity> courseEntityListMock() {
        return List.of(
                new CourseEntity(1L, "Matematicas"),
                new CourseEntity(2L, "Quimica"));
    }

    public static Optional<CourseEntity> courseMock() {
        return Optional.of(new CourseEntity(1L, "Matematicas"));
    }

    public static CourseDTO newCourseMock() {
        return new CourseDTO(3L, "Historia");
    }

    public static List<StudentDTO> studentDTOListMock() {
        return List.of(
                new StudentDTO(1L, "Juan Alberto", "juanalberto@email.com"),
                new StudentDTO(2L, "Paquito Hernandez", "erpako@email.com"));
    }

    public static List<TeacherDTO> teacherDTOListMock() {
        return List.of(
                new TeacherDTO(1L, "Andres Alguacil", "andres.alguacil@email.com"),
                new TeacherDTO(2L, "Isam Perez", "isamperez@email.com"));
    }

    public static List<CourseDTO> courseDTOListMock() {
        return List.of(
                new CourseDTO(1L, "Matematicas"),
                new CourseDTO(2L, "Quimica"));
    }

    public static CourseDTO courseDTOMock() {
        return new CourseDTO(13L, "Fisica");
    }

    public static CourseResponse courseResponseMock() {
        return new CourseResponse("Lengua Inglesa", teacherDTOListMock(), studentDTOListMock());
    }
}
