package com.microservice.course.service;

import com.microservice.course.DataProvider;
import com.microservice.course.advice.exception.CourseNotFoundException;
import com.microservice.course.client.IUserClient;
import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.http.response.CourseResponse;
import com.microservice.course.persistence.entity.CourseEntity;
import com.microservice.course.persistence.repository.ICourseRepository;
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
class ICourseServiceTest {
    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IUserClient userClient;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findAll() {
        // When
        when(courseRepository.findAll()).thenReturn(DataProvider.courseEntityListMock());
        List<CourseDTO> result = courseService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Matematicas", result.getFirst().getName());
        verify(this.courseRepository).findAll();

    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;

        // When
        when(this.courseRepository.findById(anyLong())).thenReturn(DataProvider.courseMock());
        CourseDTO courseDTO = this.courseService.findByID(id);

        // Then
        assertNotNull(courseDTO);
        assertEquals(1L, courseDTO.getId());
        assertEquals("Matematicas", courseDTO.getName());
        verify(this.courseRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        // Given
        CourseDTO courseDTO = DataProvider.newCourseMock();

        // When
        this.courseService.save(courseDTO);

        // Then
        ArgumentCaptor<CourseEntity> courseEntityArgumentCaptor = ArgumentCaptor.forClass(CourseEntity.class);
        verify(this.courseRepository).save(any(CourseEntity.class));
        verify(this.courseRepository).save(courseEntityArgumentCaptor.capture());
        assertEquals(3L, courseEntityArgumentCaptor.getValue().getId());
        assertEquals("Historia", courseEntityArgumentCaptor.getValue().getName());
    }

    @Test
    void testUpdate() {
        // Given
        Long id = 1L;
        CourseDTO courseDTO = DataProvider.newCourseMock();

        // When
        when(this.courseRepository.findById(id)).thenReturn(DataProvider.courseMock());
        this.courseService.update(courseDTO, id);

        // Then
        ArgumentCaptor<CourseEntity> courseEntityArgumentCaptor = ArgumentCaptor.forClass(CourseEntity.class);
        verify(this.courseRepository).save(any(CourseEntity.class));
        verify(this.courseRepository).save(courseEntityArgumentCaptor.capture());
        assertThrows(CourseNotFoundException.class, () -> this.courseService.update(courseDTO, 2L));
        assertEquals("Historia", courseEntityArgumentCaptor.getValue().getName());
    }

    @Test
    void testDeleteById() {
        // Given
        Long id = 1L;

        // When
        when(this.courseRepository.findById(id)).thenReturn(DataProvider.courseMock());
        this.courseService.deleteUserById(id);

        // Then
        ArgumentCaptor<CourseEntity> courseEntityArgumentCaptor = ArgumentCaptor.forClass(CourseEntity.class);
        verify(this.courseRepository).delete(any(CourseEntity.class));
        verify(this.courseRepository).delete(courseEntityArgumentCaptor.capture());
        assertEquals("Matematicas", courseEntityArgumentCaptor.getValue().getName());
        assertThrows(CourseNotFoundException.class, () -> this.courseService.deleteUserById(10L));
    }

    @Test
    void testFindStudentsAndTeachersByCourse() {
        // Given
        Long id = 1L;

        // When
        when(this.courseRepository.findById(id)).thenReturn(DataProvider.courseMock());
        when(this.userClient.findAllStudentByCourse(anyLong())).thenReturn(DataProvider.studentDTOListMock());
        when(this.userClient.findAllTeacherByCourse(anyLong())).thenReturn(DataProvider.teacherDTOListMock());
        CourseResponse response = this.courseService.findStudentsAndTeachersByCourse(id);

        // Then
        assertNotNull(response);
        assertEquals(response, CourseResponse.builder()
                .courseName("Matematicas")
                .studentDTOList(DataProvider.studentDTOListMock())
                .teachersDTOList(DataProvider.teacherDTOListMock())
                .build());
    }

}
