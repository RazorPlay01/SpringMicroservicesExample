package com.microservice.user.controller;

import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.microservice.user.DataProvider.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAll() {
        // When
        when(userService.findAll()).thenReturn(userDTOListMock());
        ResponseEntity<List<UserDTO>> userList = this.userController.getAll();

        // Then
        assertNotNull(userList);
        assertEquals(userList.getBody().get(1).getFullname(), userDTOListMock().get(1).getFullname());
        verify(this.userService).findAll();
    }

    @Test
    void testGetUserById() {
        // Given
        Long id = 1L;

        // When
        when(userService.findByID(anyLong())).thenReturn(userDTOMock());
        ResponseEntity<UserDTO> userDTOResponse = this.userController.getUserById(id);

        // Then
        assertNotNull(userDTOResponse);
        assertEquals(userDTOResponse.getBody().getFullname(), userDTOMock().getFullname());
        verify(this.userService).findByID(anyLong());
    }

    @Test
    void testCreateUser() {
        // Given
        UserDTO userDTO = userDTOMock();

        // When
        when(userService.save(any(UserDTO.class))).thenReturn(userDTOMock());
        ResponseEntity<UserDTO> userDTOResponse = this.userController.createUser(userDTO);

        // Then
        assertNotNull(userDTOResponse);
        assertEquals(userDTOResponse.getBody().getFullname(), userDTOMock().getFullname());
        verify(this.userService).save(any(UserDTO.class));
    }

    @Test
    void testUpdateUser() {
        // Given
        Long id = 1L;
        UserDTO userDTO = userDTOMock();

        // When
        when(userService.update(any(UserDTO.class), anyLong())).thenReturn(userDTOMock());
        ResponseEntity<UserDTO> userDTOResponse = this.userController.updateUser(userDTO, id);

        // Then
        assertNotNull(userDTOResponse);
        assertEquals(userDTOResponse.getBody().getFullname(), userDTOMock().getFullname());
        verify(this.userService).update(any(UserDTO.class), anyLong());
    }

    @Test
    void testDeleteUserById() {
        // Given
        Long id = 1L;

        // When
        this.userController.deleteUserById(id);

        // Then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.userService).deleteUserById(anyLong());
        verify(this.userService).deleteUserById(longArgumentCaptor.capture());
        assertEquals(id, longArgumentCaptor.getValue());
    }

    @Test
    void testFindStudentsByIdCourse() {
        // Given
        Long id = 1L;

        // When
        when(userService.findStudentsByCourseId(anyLong())).thenReturn(studentResponseMock());
        ResponseEntity<List<StudentDTO>> studentDTOList = this.userController.findStudentsByIdCourse(id);

        assertNotNull(studentDTOList);
        assertEquals(studentDTOList.getBody().getFirst(), StudentDTO.builder()
                .id(1L)
                .fullname("Juanito Alcachofa")
                .email("juano@email.com")
                .build());
        verify(this.userService).findStudentsByCourseId(anyLong());
    }

    @Test
    void testFindTeachersByIdCourse() {
        // Given
        Long id = 1L;

        // When
        when(userService.findTeachersByCourseId(anyLong())).thenReturn(teacherResponseMock());
        ResponseEntity<List<TeacherDTO>> teacherDTOList = this.userController.findTeachersByIdCourse(id);

        assertNotNull(teacherDTOList);
        assertEquals(teacherDTOList.getBody().getFirst(), TeacherDTO.builder()
                .id(1L)
                .fullname("Juanito Alcachofa")
                .email("juano@email.com")
                .build());
        verify(this.userService).findTeachersByCourseId(anyLong());
    }
}
