package com.microservice.user.service;

import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.http.response.ClientResponse;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAll();

    UserDTO findByID(Long id);

    UserDTO save(UserDTO user);

    UserDTO update(UserDTO user, Long id);

    void deleteUserById(Long id);

    List<StudentDTO> findStudentsByCourseId(Long courseId);

    List<TeacherDTO> findTeachersByCourseId(Long courseId);

    ClientResponse getCoursesById(Long courseId);

}
