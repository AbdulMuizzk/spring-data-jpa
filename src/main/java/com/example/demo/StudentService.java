package com.example.demo;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentIdCardService studentIdCardService;

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }


    public List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(
            @Param("firstName") String firstName,
            @Param("age") Integer age) {
        return studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual(firstName, age);
    }

    public void deleteStudentById(@Param("id") Long id) {
        studentRepository.deleteStudentById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public Student insertStudent(Student student) {
        studentRepository.save(student);
        StudentIdCard newStudentIdCard = studentIdCardService.generateStudentIdCard(student);
        log.info("New Student Id Card: {}", newStudentIdCard);
        return student;
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<Student> updateStudent(Student student, Long id) {
        Student foundedStudent = studentRepository.findById(id).orElse(null);
        if (foundedStudent != null) {
            foundedStudent.setFirstName(student.getFirstName());
            foundedStudent.setLastName(student.getLastName());
            foundedStudent.setAge(student.getAge());
            foundedStudent.setEmail(student.getEmail());
            studentRepository.save(foundedStudent);
            return Optional.of(foundedStudent);
        } else {
            return Optional.empty();
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Student> insertMultipleStudents(@Param("students") List<Student> students) {
        log.info("Inserting Multiple Students: {}", students);
        List <Student> newStudents = studentRepository.saveAll(students);
        newStudents.forEach(studentIdCardService::generateStudentIdCard);
        return newStudents;
    }

    public boolean studentExistsById(@NonNull Long id) {
        return studentRepository.existsById(id);
    }
}
