package com.example.demo.services;
import com.example.demo.entities.*;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.EnrolmentRepository;
import com.example.demo.repositories.StudentRepository;
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

    private final CourseRepository courseRepository;

    private final EnrolmentRepository enrolmentRepository;
    private final BookService bookService;
    private final EnrolmentService enrolmentService;

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

    @Transactional
    public Optional<Enrolment> enrollStudentToCourse(@NonNull Long studentId, @NonNull Long courseId) {
            Optional<Course> course = courseRepository.findCourseById(courseId);
            Optional<Student> student = studentRepository.findById(studentId);
            if (course.isPresent() && student.isPresent()) {
                EnrolmentId enrolmentId = new EnrolmentId();
                enrolmentId.setStudentId(student.get().getId());
                enrolmentId.setCourseId(course.get().getId());
                Enrolment enrolment = new Enrolment();
                enrolment.setEnrolmentId(enrolmentId);
                enrolment.setStudent(student.get());
                enrolment.setCourse(course.get());
                Enrolment newEnrolment = enrolmentRepository.save(enrolment);
                return Optional.of(newEnrolment);
            } else {
                return Optional.empty();
            }
    }

    public List<Enrolment> getEnrolments() {
        return enrolmentRepository.findAll();
    }

    @Transactional
    public void deleteByIdAndDetails(Long studentId) {
        bookService.deleteBookByStudentId(studentId);
        enrolmentService.deleteEnrolmentByStudentId(studentId);
        studentIdCardService.deleteStudentIdCardByStudentId(studentId);
        this.deleteStudentById(studentId);

    }
}
