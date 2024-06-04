package com.microservice.user.service;

import com.microservice.user.client.ICourseClient;
import com.microservice.user.controller.dto.CourseDTO;
import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.http.response.ClientResponse;
import com.microservice.user.persistence.entity.UserEntity;
import com.microservice.user.advice.exception.UserNotFoundException;
import com.microservice.user.persistence.entity.UserType;
import com.microservice.user.persistence.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    ICourseClient courseClient;

    @Override
    public List<UserDTO> findAll() {
        List<UserEntity> userDTOS = (List<UserEntity>) this.repository.findAll();

        return userDTOS.stream().map(entity -> this.modelMapper.map(entity, UserDTO.class)).toList();
    }

    @Override
    public UserDTO findByID(Long id) {
        UserEntity user = this.repository.findById(id).orElseGet(UserEntity::new);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        UserEntity userEntity = this.modelMapper.map(userDTO, UserEntity.class);
        UserEntity userSaved = this.repository.save(userEntity);

        return this.modelMapper.map(userSaved, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        UserEntity currentUserEntity = this.repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario con id " + id + " no existe."));

        currentUserEntity.setUsername(userDTO.getUsername());
        currentUserEntity.setPassword(userDTO.getPassword());
        currentUserEntity.setEmail(userDTO.getEmail());
        currentUserEntity.setCourseId(userDTO.getCourseId());
        currentUserEntity.setUserType(userDTO.getUserType());

        UserEntity userUpdated = this.repository.save(currentUserEntity);

        return this.modelMapper.map(userUpdated, UserDTO.class);
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity currentUserEntity = this.repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario con id " + id + " no existe."));

        this.repository.delete(currentUserEntity);
    }

    @Override
    public List<StudentDTO> findStudentsByCourseId(Long courseId) {
        List<UserEntity> students = new ArrayList<>(this.repository.findAllByCourseId(courseId).stream().filter(user ->
                user.getUserType().equals(UserType.STUDENT)).toList());

        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (UserEntity student : students) {
            StudentDTO studentDTO = StudentDTO.builder()
                    .id(student.getId())
                    .fullname(student.getFullname())
                    .email(student.getEmail())
                    .courseName(getCoursesById(student.getCourseId()).getName())
                    .build();

            studentDTOS.add(studentDTO);
        }

        return studentDTOS;
        //return students.stream().map(entity -> this.modelMapper.map(entity, StudentDTO.class)).toList();
    }

    @Override
    public List<TeacherDTO> findTeachersByCourseId(Long courseId) {
        List<UserEntity> teachers = new ArrayList<>(this.repository.findAllByCourseId(courseId).stream().filter(user ->
                user.getUserType().equals(UserType.TEACHER)).toList());

        List<TeacherDTO> teacherDTOS = new ArrayList<>();

        for (UserEntity teacher : teachers) {
            TeacherDTO studentDTO = TeacherDTO.builder()
                    .id(teacher.getId())
                    .fullname(teacher.getFullname())
                    .email(teacher.getEmail())
                    .courseName(getCoursesById(teacher.getCourseId()).getName())
                    .build();

            teacherDTOS.add(studentDTO);
        }

        return teacherDTOS;
        //return teachers.stream().map(entity -> this.modelMapper.map(entity, TeacherDTO.class)).toList();
    }

    @Override
    public ClientResponse getCoursesById(Long id) {
        CourseDTO courseDTO = courseClient.findCourseById(id);

        return ClientResponse.builder()
                .name(courseDTO.getName())
                .build();
    }


}
