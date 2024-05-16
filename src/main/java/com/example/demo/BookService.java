package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public Book findById (Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public Optional<Book> addNewBook (Book book, Student student) {
        if (student == null) {
            return Optional.empty();
        }
        Book newBook = new Book();
        newBook.setBookName(book.getBookName());
        newBook.setStudent(student);
        newBook.setCreatedAt(LocalDateTime.now());
        newBook = bookRepository.save(newBook);
        return Optional.of(newBook);
    }

    public boolean deleteBookByStudentId(Long studentId) {
        if (studentId == null) {
            return false;
        }
        else {
            bookRepository.deleteBookByStdId(studentId);
            return true;
        }
    }

    public Optional<Book> findBookByStudentId(Long studentId) {
        if(studentId == null) {
            return Optional.empty();
        } else {
            return bookRepository.findByStudentId(studentId);
        }
    }

    public Optional<Book> updateBook (Long bookId, Long studentId, Book book) {
        if (studentId == null) {
            return Optional.empty();
        }
        Optional<Book> bookFound = bookRepository.findById(bookId);
        Optional<Student> studentFound = studentRepository.findById(studentId);
        if (bookFound.isPresent() && studentFound.isPresent()) {
            bookFound.get().setBookName(book.getBookName());
            bookFound.get().setStudent(studentFound.get());
            bookFound.get().setCreatedAt(LocalDateTime.now());
            bookRepository.save(bookFound.get());
            return bookFound;
        } else {
            return Optional.empty();
        }
    }

}
