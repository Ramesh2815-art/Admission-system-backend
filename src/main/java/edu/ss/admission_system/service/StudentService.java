package edu.ss.admission_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.ss.admission_system.dto.Student;
import edu.ss.admission_system.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        // Check if email or phone number already exists
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (studentRepository.findByPhoneNumber(student.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    // Update status of student by id
    public void updateStudentStatus(Long id, String status) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setStatus(status);  // Set the new status
            studentRepository.save(student);  // Save the updated student to the database
        } else {
            throw new RuntimeException("Student not found with id " + id);
        }
    }
    public Student authenticateUser(String email, String password) {
        // Look for the student by email
        Optional<Student> studentOpt = studentRepository.findByEmail(email);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // Check if password matches
            if (student.getPassword().equals(password)) {
                return student; // Return the student if password is correct
            }
        }

        return null; // Return null if no matching student or incorrect password
    }
    public Optional<Student> getStudentByEmailOrPhone(String email, String phoneNumber) {
        return studentRepository.findByEmail(email)
                .or(() -> studentRepository.findByPhoneNumber(phoneNumber));
    }

}


