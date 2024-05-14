package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student-id-cards")
@AllArgsConstructor
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
