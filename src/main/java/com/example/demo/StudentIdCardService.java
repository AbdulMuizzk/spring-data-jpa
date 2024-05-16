package com.example.demo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;


    private String randomIdGenerator() {
        int length = 10; // Length of the random string
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Generate a random digit (0-9)
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

    public void deleteStudentIdCardByStudentId(@NonNull Long studentId) {
        studentIdCardRepository.deleteStudentIdCardByStudentId(studentId);
    }

    public boolean existsStudentIdCardByStudentId (@Param("id") Long studentId) {
        return studentIdCardRepository.existsByStudentId(studentId);
    }

}
