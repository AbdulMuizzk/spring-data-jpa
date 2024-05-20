package com.example.demo.repositories;


import com.example.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("select s from student_books s where s.student.id = ?1")
    Optional<List<Book>> findByStudent_Id(Long id);


    @Modifying
    @Query("DELETE FROM student_books b WHERE b.student.id  = :id")
    void deleteBookByStdId(Long id);




}
