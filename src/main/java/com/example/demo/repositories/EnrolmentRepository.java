package com.example.demo.repositories;

import com.example.demo.entities.Enrolment;
import com.example.demo.entities.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId>{



    @Modifying
    @Query("DELETE from Enrolment e where e.enrolmentId.studentId = :studentId")
    void deleteByEnrolmentId_StudentId(Long studentId);
}
