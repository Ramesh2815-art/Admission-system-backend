package edu.ss.admission_system.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ss.admission_system.dto.LoginRequest;
import edu.ss.admission_system.dto.Student;
import edu.ss.admission_system.service.StudentService;

@RestController
public class AuthController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/api/login")
    public ResponseEntity<Student> login(@RequestBody LoginRequest loginRequest) {
        // Make sure loginRequest has the necessary fields
        if (loginRequest.getEmail() == null && loginRequest.getPhoneNumber() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Student> student = studentService.getStudentByEmailOrPhone(
                loginRequest.getEmail(),
                loginRequest.getPassword()
                
        );

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());  // Return student details
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // Unauthorized if student not found
        }
    }


}
