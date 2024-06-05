package com.microservice.user.controller;

import com.microservice.user.controller.dto.StudentDTO;
import com.microservice.user.controller.dto.TeacherDTO;
import com.microservice.user.controller.dto.UserDTO;
import com.microservice.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users")
public class UserController {
    IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(this.userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findByID(id), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        userDTO.setAccountNoExpired(true);
        userDTO.setAccountNoLocked(true);
        userDTO.setEnabled(true);
        userDTO.setCredentialNoExpired(true);
        userDTO.setRegisterDate(new Date());
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO, @PathVariable Long id) {
        return new ResponseEntity<>(userService.update(userDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/student/{courseId}")
    @Operation(description = "Find students by course ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<StudentDTO>> findStudentsByIdCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(userService.findStudentsByCourseId(courseId));
    }

    @GetMapping("/teacher/{courseId}")
    public ResponseEntity<List<TeacherDTO>> findTeachersByIdCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(userService.findTeachersByCourseId(courseId));
    }
}
