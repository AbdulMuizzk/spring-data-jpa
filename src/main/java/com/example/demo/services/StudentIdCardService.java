package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.entities.StudentIdCard;
import com.example.demo.repositories.StudentIdCardRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;
    private final Random random = new Random();


    private String randomIdGenerator() {
        int length = 10; // Length of the random string
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(this.random.nextInt(10)); // Generate a random digit (0-9)
        }
        return sb.toString();
    }
    public StudentIdCard generateStudentIdCard (Student student) {
        log.info("Generating a Student Id Card for the following Student: {}", student);
        StudentIdCard studentIdCard = new StudentIdCard();
        studentIdCard.setCardNumber(this.randomIdGenerator());
        studentIdCard.setStudent(student);
        return studentIdCardRepository.save(studentIdCard);
    }
    public StudentIdCard getStudentIdCardBasedOnStudentId (Long studentId) {
        return studentIdCardRepository.findByStudentId(studentId);
    }

    @Transactional
    public void deleteStudentIdCardByStudentId(@NonNull Long studentId) {
        studentIdCardRepository.deleteByStudentId(studentId);
    }

}
