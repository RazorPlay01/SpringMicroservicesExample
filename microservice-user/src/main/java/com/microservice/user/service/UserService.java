package com.microservice.user.service;

import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.persistence.entity.UserEntity;
import com.microservice.user.advice.exception.UserNotFoundException;
import com.microservice.user.persistence.entity.UserType;
import com.microservice.user.persistence.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserDTO> getAll() {
        List<UserEntity> userDTOS = (List<UserEntity>) this.repository.findAll();

        return userDTOS.stream().map(entity -> this.modelMapper.map(entity, UserDTO.class)).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
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

        return students.stream().map(entity -> this.modelMapper.map(entity, StudentDTO.class)).toList();
    }

    @Override
    public List<TeacherDTO> findTeachersByCourseId(Long courseId) {
        List<UserEntity> teachers = new ArrayList<>(this.repository.findAllByCourseId(courseId).stream().filter(user ->
                user.getUserType().equals(UserType.TEACHER)).toList());

        return teachers.stream().map(entity -> this.modelMapper.map(entity, TeacherDTO.class)).toList();
    }
}
