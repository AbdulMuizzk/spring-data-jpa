package com.example.demo;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByStudentId(Long studentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM student_books b WHERE b.student.id  = :id")
    void deleteBookByStdId(@Param("id") Long studentId);
}
