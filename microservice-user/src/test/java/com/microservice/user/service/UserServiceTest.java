package com.microservice.user.service;

import com.microservice.user.DataProvider;
import com.microservice.user.advice.exception.UserNotFoundException;
import com.microservice.user.client.ICourseClient;
import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.http.response.ClientResponse;
import com.microservice.user.persistence.entity.UserEntity;
import com.microservice.user.persistence.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private ICourseClient courseClient;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindAll() {
        // When
        when(userRepository.findAll()).thenReturn(DataProvider.userEntityListMock());
        List<UserDTO> result = this.userService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Juanito Alcachofa", result.getFirst().getFullname());
        assertEquals("Juano", result.getFirst().getUsername());
        assertEquals("12345678", result.getFirst().getPassword());
        verify(this.userRepository).findAll();
    }

    @Test
    void testFindByID() {
        // Given
        Long id = 1L;

        // When
        when(this.userRepository.findById(anyLong())).thenReturn(DataProvider.userMock());
        UserDTO userDTO = this.userService.findByID(id);

        // Then
        assertNotNull(userDTO);
        assertEquals("Juanito Alcachofa", userDTO.getFullname());
        assertEquals("12345678", userDTO.getPassword());
        assertEquals("Juano", userDTO.getUsername());
        verify(this.userRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        // Given
        UserDTO userDTO = DataProvider.newUserMock();

        // When
        this.userService.save(userDTO);

        // Then
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.userRepository).save(any(UserEntity.class));
        verify(this.userRepository).save(userEntityArgumentCaptor.capture());
        assertEquals("Juanito Alcachofa", userEntityArgumentCaptor.getValue().getFullname());
        assertEquals("87654321", userEntityArgumentCaptor.getValue().getPassword());
        assertEquals("Juano", userEntityArgumentCaptor.getValue().getUsername());
    }

    @Test
    void testUpdate() {
        // Given
        Long id = 1L;
        UserDTO userDTO = DataProvider.newUserMock();

        // When
        when(this.userRepository.findById(id)).thenReturn(DataProvider.userMock());
        this.userService.update(userDTO, id);

        // Then
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.userRepository).save(any(UserEntity.class));
        verify(this.userRepository).save(userEntityArgumentCaptor.capture());
        assertThrows(UserNotFoundException.class, () -> this.userService.update(userDTO, 2L));
        assertEquals("Juanito Alcachofa", userEntityArgumentCaptor.getValue().getFullname());
        assertEquals("87654321", userEntityArgumentCaptor.getValue().getPassword());
        assertEquals("Juano", userEntityArgumentCaptor.getValue().getUsername());
    }

    @Test
    void testDeleteUserById() {
        // Given
        Long id = 1L;

        // When
        when(this.userRepository.findById(id)).thenReturn(DataProvider.userMock());
        this.userService.deleteUserById(id);

        // Then
        ArgumentCaptor<UserEntity> userEntityArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.userRepository).delete(any(UserEntity.class));
        verify(this.userRepository).delete(userEntityArgumentCaptor.capture());
        assertEquals("Juanito Alcachofa", userEntityArgumentCaptor.getValue().getFullname());
        assertThrows(UserNotFoundException.class, () -> this.userService.deleteUserById(10L));
    }

    @Test
    void testFindStudentsByCourseId() {
        // Given
        Long id = 1L;

        // When
        when(this.userRepository.findAllByCourseId(anyLong())).thenReturn(DataProvider.userEntityListMock());
        List<StudentDTO> studentDTOS = this.userService.findStudentsByCourseId(id);

        // Then
        assertEquals(studentDTOS.getFirst().getFullname(), DataProvider.studentDTOListMock().getFirst().getFullname());
        verify(this.userRepository).findAllByCourseId(anyLong());
    }

    @Test
    void testFindTeachersByCourseId() {
        // Given
        Long id = 1L;

        // When
        when(this.userRepository.findAllByCourseId(anyLong())).thenReturn(DataProvider.userEntityListMock());
        List<TeacherDTO> teacherDTOS = this.userService.findTeachersByCourseId(id);

        // Then
        assertEquals(teacherDTOS.getFirst().getFullname(), DataProvider.teacherDTOListMock().getFirst().getFullname());
        verify(this.userRepository).findAllByCourseId(anyLong());
    }

    @Test
    void testGetCoursesById() {
        // Given
        Long id = 1L;

        // When
        when(this.courseClient.findCourseById(anyLong())).thenReturn(DataProvider.courseDTOMock());
        ClientResponse clientResponse = this.userService.getCoursesById(id);

        // Then
        assertNotNull(clientResponse);
        assertEquals(clientResponse.getName(), DataProvider.courseDTOMock().getName());
        verify(this.courseClient).findCourseById(anyLong());
    }


}
