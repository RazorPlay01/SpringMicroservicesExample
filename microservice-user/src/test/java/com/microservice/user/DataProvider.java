package com.microservice.user;

import com.microservice.user.controller.dto.CourseDTO;
import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.persistence.entity.UserEntity;
import com.microservice.user.persistence.entity.UserType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DataProvider {
    public static List<UserEntity> userEntityListMock() {
        return List.of(
                new UserEntity(1L, "Juano", "12345678", "Juanito Alcachofa", "juano@email.com", new Date(), new Date(), UserType.STUDENT, true, true, true, true, 1L),
                new UserEntity(2L, "Marcos", "87654321", "Marcos Mer", "mar@email.com", new Date(), new Date(), UserType.TEACHER, true, true, true, true, 1L));
    }

    public static Optional<UserEntity> userMock() {
        return Optional.of(new UserEntity(1L, "Juano", "12345678", "Juanito Alcachofa", "juano@email.com", new Date(), new Date(), UserType.STUDENT, true, true, true, true, 1L));
    }

    public static UserDTO newUserMock() {
        return new UserDTO(1L, "Juano", "87654321", "Juanito Alcachofa", "juano@email.com", new Date(), new Date(), UserType.STUDENT, true, true, true, true, 1L);
    }

    public static List<StudentDTO> studentDTOListMock() {
        return List.of(
                new StudentDTO(1L, "Juanito Alcachofa", "juano@email.com"),
                new StudentDTO(2L, "Marcos Mer", "mar@email.com")
        );
    }

    public static List<TeacherDTO> teacherDTOListMock() {
        return List.of(
                new TeacherDTO(2L, "Marcos Mer", "mar@email.com")
        );
    }

    public static CourseDTO courseDTOMock() {
        return new CourseDTO(1L, "Matematica");
    }

    public static List<UserDTO> userDTOListMock() {
        return List.of(
                new UserDTO(1L, "Juano", "12345678", "Juanito Alcachofa", "juano@email.com", new Date(), new Date(), UserType.STUDENT, true, true, true, true, 1L),
                new UserDTO(2L, "Marcos", "87654321", "Marcos Mer", "mar@email.com", new Date(), new Date(), UserType.TEACHER, true, true, true, true, 1L)
        );
    }

    public static UserDTO userDTOMock() {
        return new UserDTO(1L, "Juano", "12345678", "Juanito Alcachofa", "juano@email.com", new Date(), new Date(), UserType.STUDENT, true, true, true, true, 1L);
    }

    public static List<StudentDTO> studentResponseMock() {
        return List.of(new StudentDTO(1L, "Juanito Alcachofa", "juano@email.com"));
    }

    public static List<TeacherDTO> teacherResponseMock() {
        return List.of(new TeacherDTO(1L, "Juanito Alcachofa", "juano@email.com"));
    }
}
