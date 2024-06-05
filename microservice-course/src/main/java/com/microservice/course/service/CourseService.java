package com.microservice.course.service;

import com.microservice.course.advice.exception.CourseNotFoundException;
import com.microservice.course.client.IUserClient;
import com.microservice.course.controller.dto.CourseDTO;
import com.microservice.course.controller.dto.StudentDTO;
import com.microservice.course.controller.dto.TeacherDTO;
import com.microservice.course.http.response.CourseResponse;
import com.microservice.course.persistence.entity.CourseEntity;
import com.microservice.course.persistence.repository.ICourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    ICourseRepository repository;
    IUserClient userClient;

    @Autowired
    public CourseService(ICourseRepository repository, IUserClient userClient) {
        this.repository = repository;
        this.userClient = userClient;
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CourseDTO> findAll() {
        List<CourseEntity> courseEntities = (List<CourseEntity>) this.repository.findAll();

        return courseEntities.stream().map(entity -> this.modelMapper.map(entity, CourseDTO.class)).toList();
    }

    @Override
    public CourseDTO findByID(Long id) {
        CourseEntity course = this.repository.findById(id).orElseGet(CourseEntity::new);
        return this.modelMapper.map(course, CourseDTO.class);
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO) {
        CourseEntity courseEntity = this.modelMapper.map(courseDTO, CourseEntity.class);
        this.repository.save(courseEntity);

        return this.modelMapper.map(courseEntity, CourseDTO.class);
    }

    @Override
    public CourseDTO update(CourseDTO courseDTO, Long id) {
        CourseEntity currentUserEntity = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Curso con id " + id + " no existe."));

        currentUserEntity.setName(courseDTO.getName());
        this.repository.save(currentUserEntity);

        return this.modelMapper.map(currentUserEntity, CourseDTO.class);
    }

    @Override
    public void deleteCourseById(Long id) {
        CourseEntity currentCourseEntity = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException("Curso con id " + id + " no existe."));

        this.repository.delete(currentCourseEntity);
    }

    @Override
    public CourseResponse findStudentsAndTeachersByCourse(Long courseId) {
        CourseEntity course = repository.findById(courseId).orElseThrow();

        List<StudentDTO> studentDTOList = userClient.findAllStudentByCourse(course.getId());
        List<TeacherDTO> teacherDTOList = userClient.findAllTeacherByCourse(course.getId());

        return CourseResponse.builder()
                .courseName(course.getName())
                .studentDTOList(studentDTOList)
                .teachersDTOList(teacherDTOList)
                .build();
    }
}
