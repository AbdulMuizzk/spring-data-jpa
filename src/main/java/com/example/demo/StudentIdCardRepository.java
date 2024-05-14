package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Long> {

    StudentIdCard findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);

    @Transactional
    @Modifying
    int deleteStudentIdCardByStudentId(Long studentId);
}
