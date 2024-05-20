package com.example.demo.repositories;

import com.example.demo.entities.Student;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository <Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsById(@NonNull Long id);

    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) = LOWER(:firstName) AND s.age >= :age")
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(String firstName, Integer age);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    void deleteStudentById(Long id);

}
