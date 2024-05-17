package com.example.demo.repositories;

import com.example.demo.entities.Enrolment;
import com.example.demo.entities.EnrolmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId>{
}
