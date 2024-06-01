package com.microservice.user.controller.dto;

import com.microservice.user.persistence.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;
    private String fullname;
    private String email;
    private String courseName;
}
