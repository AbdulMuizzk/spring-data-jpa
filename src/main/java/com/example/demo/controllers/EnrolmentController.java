package com.example.demo.controllers;

import com.example.demo.dtos.EnrolmentDto;
import com.example.demo.entities.Enrolment;
import com.example.demo.exceptions.GetAllEnrolmentException;
import com.example.demo.mappers.EnrolmentMapper;
import com.example.demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enrolments")
@RequiredArgsConstructor
public class EnrolmentController {
    private final EnrolmentMapper enrolmentMapper;
    private final StudentService studentService;
    @GetMapping
    public ResponseEntity<List<EnrolmentDto>> getEnrolments() throws GetAllEnrolmentException {
        List<Enrolment> studentEnrolments = studentService.getEnrolments();
        if (studentEnrolments.isEmpty()) {
            throw new GetAllEnrolmentException("No enrolments found");
        }
        // would have to map to a generic dto (Throw exception when the code is misbehaving)
        List<EnrolmentDto> enrolmentDtosList = studentEnrolments.stream()
                .map(enrolmentMapper::toEnrolmentDto)
                .toList();
        return ResponseEntity.ok(enrolmentDtosList);
    }
}
