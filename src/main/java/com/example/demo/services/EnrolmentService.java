package com.example.demo.services;

import com.example.demo.repositories.EnrolmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EnrolmentService {
    private final EnrolmentRepository enrolmentRepository;

    @Transactional
    public void deleteEnrolmentByStudentId(Long studentId) {
        enrolmentRepository.deleteByEnrolmentId_StudentId(studentId);
    }
}
