package com.microservice.course.controller;

import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.http.response.CourseResponse;
import com.microservice.course.service.CourseService;
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
import static com.microservice.course.DataProvider.*;


@ExtendWith(MockitoExtension.class)
class CourseControllerTest {
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void testGetAll() {
        // When
        when(courseService.findAll()).thenReturn(courseDTOListMock());
        ResponseEntity<List<CourseDTO>> courseList = this.courseController.getAll();

        // Then
        assertNotNull(courseList);
        assertEquals(courseList.getBody().get(1), courseDTOListMock().get(1));
        verify(this.courseService).findAll();
    }

    @Test
    void getCourseById() {
        // Given
        Long id = 1L;

        // When
        when(courseService.findByID(anyLong())).thenReturn(courseDTOMock());
        ResponseEntity<CourseDTO> courseDTOResponse = this.courseController.getCourseById(id);

        // Then
        assertNotNull(courseDTOResponse);
        assertEquals(courseDTOResponse.getBody(), courseDTOMock());
        verify(this.courseService).findByID(anyLong());
    }

    @Test
    void createCourse() {
        // Given
        CourseDTO courseDTO = courseDTOMock();

        // When
        when(courseService.save(any(CourseDTO.class))).thenReturn(courseDTOMock());
        ResponseEntity<CourseDTO> courseDTOResponse = this.courseController.createCourse(courseDTO);

        // Then
        assertNotNull(courseDTOResponse);
        assertEquals(courseDTOResponse.getBody(), courseDTOMock());
        verify(this.courseService).save(any(CourseDTO.class));
    }

    @Test
    void updateCourse() {
        // Given
        Long id = 1L;
        CourseDTO courseDTO = courseDTOMock();

        // When
        when(courseService.update(any(CourseDTO.class), anyLong())).thenReturn(courseDTOMock());
        ResponseEntity<CourseDTO> courseDTOResponse = this.courseController.updateCourse(courseDTO, id);

        // Then
        assertNotNull(courseDTOResponse);
        assertEquals(courseDTOResponse.getBody(), courseDTOMock());
        verify(this.courseService).update(any(CourseDTO.class), anyLong());
    }

    @Test
    void deleteCourseById() {
        // Given
        Long id = 1L;

        // When
        this.courseController.deleteCourseById(id);

        // Then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.courseService).deleteCourseById(anyLong());
        verify(this.courseService).deleteCourseById(longArgumentCaptor.capture());
        assertEquals(id, longArgumentCaptor.getValue());

    }

    @Test
    void findAllCourseInfo() {
        // Given
        Long id = 1L;

        // When
        when(courseService.findStudentsAndTeachersByCourse(anyLong())).thenReturn(courseResponseMock());
        ResponseEntity<CourseResponse> courseInfoResponse = this.courseController.findAllCourseInfo(id);

        // Then
        assertNotNull(courseInfoResponse);
        assertEquals(courseInfoResponse.getBody(), CourseResponse.builder()
                .courseName("Lengua Inglesa")
                .teachersDTOList(teacherDTOListMock())
                .studentDTOList(studentDTOListMock())
                .build());
        verify(this.courseService).findStudentsAndTeachersByCourse(anyLong());
    }
}
