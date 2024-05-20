package com.example.demo.controllers;

import com.example.demo.dtos.EnrolmentDto;
import com.example.demo.dtos.StudentDto;
import com.example.demo.entities.Enrolment;
import com.example.demo.entities.Student;
import com.example.demo.exceptions.*;
import com.example.demo.mappers.EnrolmentMapper;
import com.example.demo.mappers.StudentMapper;
import com.example.demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Rest Compliant Any documentations
// Student Resource -> get update put format of the URLS for any Rest Based entity
// naming conventions of the URL should be changed
// Rest Documentation compliant

@RestController
@RequestMapping("students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final EnrolmentMapper enrolmentMapper;
    private final StudentMapper studentMapper;

    @GetMapping("/{email}")
    public ResponseEntity<StudentDto> getStudentsByEmail(@PathVariable String email) throws StudentNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Optional<Student> student = studentService.getStudentByEmail(email);
        return student
                .map(value -> ResponseEntity.ok(studentMapper.toStudentDto(value)))
                .orElseThrow(() -> new StudentNotFoundException("Student with email " + email + " not found"));

    }
    @GetMapping
    public ResponseEntity<List<StudentDto>> selectStudentWhereFirstNameAndAgeGreaterOrEqual(@RequestParam String firstName, @RequestParam Integer age) throws StudentNotFoundException {
        if ((firstName == null || firstName.isEmpty()) || (age == null) || (age < 0)) {
            throw new IllegalArgumentException("First name cannot be null or empty and age cannot be null or less than 0");
        }
        List<Student> studentsFound = studentService.selectStudentWhereFirstNameAndAgeGreaterOrEqual(firstName, age);
        if (studentsFound.isEmpty()) {
            throw new StudentNotFoundException("No students found with first name " 
                    + firstName + " and age greater or equal to " + age);
        }
        List<StudentDto> studentDtosList = studentsFound.stream()
                .map(studentMapper::toStudentDto)
                .toList();
        return ResponseEntity.ok(studentDtosList);
    }
    @PostMapping
    public ResponseEntity<StudentDto> insertStudent(@RequestBody Student student) throws StudentInsertionException {
        if (student == null) {
           throw new IllegalArgumentException("Student Object cannot be null");
        }
        Student std = studentService.insertStudent(student);
        if (std != null) {
            return ResponseEntity.ok(studentMapper.toStudentDto(std));
        } else {
            throw new StudentInsertionException("An error occurred while creating a student");
        }
    }

    @PatchMapping
    public ResponseEntity<StudentDto> updateStudentDetails(@RequestBody Student student) throws StudentUpdationException {
        if (student == null || student.getId() == null) {
            throw new IllegalArgumentException("Student object or student id cannot be null");
        }
        Optional<Student> updatedStudent =  studentService.updateStudent(student, student.getId());
        return updatedStudent
                .map(value -> ResponseEntity.ok(studentMapper.toStudentDto(value)))
                .orElseThrow(() -> new StudentUpdationException("An error occurred while updating student with id " + student.getId()));
    }

    @PostMapping("/bulk-insert")
    public ResponseEntity<List<StudentDto>> insertMultipleStudents(@RequestBody List<Student> students) throws StudentInsertionException {
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("Students list cannot be null or empty");
        }
        List<Student> studentsInserted = studentService.insertMultipleStudents(students);
        List<StudentDto> studentInsertedDto = studentsInserted.stream()
                .map(studentMapper::toStudentDto)
                .toList();
        return ResponseEntity.ok(studentInsertedDto);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) throws StudentNotFoundException {
        if (studentId == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        studentService.deleteByIdAndDetails(studentId);
    }

    @PostMapping("/{studentId}/{courseId}/enroll")
    public ResponseEntity<EnrolmentDto> enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) throws StudentEnrolmentException {
        if (studentId == null || courseId == null) {
            throw new IllegalArgumentException("Student id or course id cannot be null");
        }
        Optional<Enrolment> newStudentEnrolment = studentService.enrollStudentToCourse(studentId, courseId);
        if(newStudentEnrolment.isEmpty()) {
            throw new StudentEnrolmentException("An error occurred while enrolling student with id " + studentId + " to course with id " + courseId);
        }
        EnrolmentDto enrolmentDto = enrolmentMapper.toEnrolmentDto(newStudentEnrolment.get());
        return ResponseEntity.ok(enrolmentDto);
    }
}
