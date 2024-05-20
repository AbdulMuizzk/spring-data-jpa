package com.example.demo.controllers;

import com.example.demo.entities.StudentIdCard;
import com.example.demo.services.StudentIdCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student-id-cards")
@RequiredArgsConstructor
public class StudentIdCardController {
    private final StudentIdCardService studentIdCardService;
    @GetMapping
    public ResponseEntity<StudentIdCard> getStudentIdCard(@RequestParam(required = false) Long studentId) {
        if (studentId == null) {
            ResponseEntity.badRequest().body(null);
        }
        StudentIdCard idCard = studentIdCardService.getStudentIdCardBasedOnStudentId(studentId);
        return idCard != null ? ResponseEntity.ok(idCard) : ResponseEntity.notFound().build();
    }
}
