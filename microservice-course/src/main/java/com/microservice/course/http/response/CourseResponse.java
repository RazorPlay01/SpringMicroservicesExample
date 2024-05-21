package com.microservice.course.http.response;

import com.microservice.course.controller.dto.StudentDTO;
import com.microservice.course.controller.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private String courseName;
    private List<TeacherDTO> teachersDTOList;
    private List<StudentDTO> studentDTOList;
}
