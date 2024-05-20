package com.example.demo.repositories;

import com.example.demo.entities.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {

    StudentIdCard findByStudentId(Long studentId);

    @Modifying
    @Query("DELETE FROM StudentIdCard s WHERE s.student.id = :studentId")
    void deleteByStudentId(Long studentId);
}
