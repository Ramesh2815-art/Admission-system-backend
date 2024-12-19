package edu.ss.admission_system.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ss.admission_system.dto.Student;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom Query Methods
    Optional<Student> findByEmail(String email);

    Optional<Student> findByPhoneNumber(String phoneNumber);
    
    // Custom method to find a student by ID
    Optional<Student> findById(Long id);

    // Method to update status
    @Modifying
    @Query("UPDATE Student s SET s.status = ?1 WHERE s.id = ?2")
    void updateStatus(String status, Long id);
}
