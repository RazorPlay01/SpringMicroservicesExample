package com.microservice.user.controller.dto;

import com.microservice.user.persistence.entity.UserType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String fullname;
    @Email
    @NotBlank
    private String email;
    private Date birthDate;
    private Date registerDate;

    private UserType userType;

    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;

    private Long courseId;

}
