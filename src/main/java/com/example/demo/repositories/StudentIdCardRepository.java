package com.example.demo.repositories;

import com.example.demo.entities.StudentIdCard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> {

    StudentIdCard findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);

    @Transactional
    @Modifying
    void deleteStudentIdCardByStudentId(Long studentId);
}
