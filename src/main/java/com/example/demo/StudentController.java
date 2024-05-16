package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor()

// Rest Compliant Any documentations
// Student Resource -> get update put format of the URLS for any Rest Based entity
// naming conventions of the URL should be changed
// Rest Documentation compliant

public class StudentController {

    private final StudentService studentService;

    private final StudentIdCardService studentIdCardService;

    @GetMapping("/get-student-by-email")
    public ResponseEntity<?> getStudentsByEmail(@RequestParam(required = false) String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<?> student = studentService.getStudentByEmail(email);
        if (student.isPresent()) {
            return student
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get-student-by-firstname-and-age")
    public ResponseEntity<List<Student>> selectStudentWhereFirstNameAndAgeGreaterOrEqual(@RequestParam(required = false) String firstName, @RequestParam(required = false) Integer age) {
        if ((firstName == null || firstName.isEmpty()) || (age == null) || (age < 0)) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Student> studentsFound = studentService.selectStudentWhereFirstNameAndAgeGreaterOrEqual(firstName, age);
        return ResponseEntity.ok(studentsFound);
    }
    @PostMapping
    public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
        if (student == null) {
           return ResponseEntity.badRequest().body(null);
        }
        Student std = studentService.insertStudent(student);
        return std != null ? ResponseEntity.ok(std) : ResponseEntity.badRequest().body(null);
    }

    @PatchMapping
    public ResponseEntity<Student> updateStudentDetails(@RequestParam Long studentId, @RequestBody Student student) {
        if(student == null || studentId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Student> updatedStudent =  studentService.updateStudent(student, studentId);
        return updatedStudent
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("insert-multiple-students")
    public ResponseEntity<List<Student>> insertMultipleStudents(@RequestBody List<Student> students) {
        if (students == null || students.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Student> studentsInserted = studentService.insertMultipleStudents(students);

        return studentsInserted != null ? ResponseEntity.ok(studentsInserted) : ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping
    public boolean deleteStudent(@RequestParam Long studentId) {
        boolean studentFound = studentService.studentExistsById(studentId);
        boolean studentIdCardFound = studentIdCardService.existsStudentIdCardByStudentId(studentId);
        if(studentFound && studentIdCardFound) {
            studentIdCardService.deleteStudentIdCardByStudentId(studentId);
            studentService.deleteStudentById(studentId);
            return true;
        } else {
            return false;
        }
    }
}
