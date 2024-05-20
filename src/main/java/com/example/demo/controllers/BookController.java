package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.services.BookService;
import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) Long studentId) {
        if (studentId == null) {
            return ResponseEntity.ok(bookService.findAll());
        }
        Optional<List<Book>> booksFound =  bookService.findBookByStudentId(studentId);
        return booksFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @DeleteMapping("/{studentId}")
    public void deleteBookByStudentId(@PathVariable Long studentId) {
        bookService.deleteBookByStudentId(studentId);
    }
    @PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        if (book.getStudent().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Student> student = studentService.getStudentById(book.getStudent().getId());
        if (student.isPresent()) {
            Optional<Book> newBook = bookService.addNewBook(book, student.get());
            return newBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PatchMapping
    public ResponseEntity<Book> updateBook(@RequestParam Long bookId, @RequestParam Long studentId, @RequestBody Book book) {
        if (studentId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            Optional<Book> updatedBook = bookService.updateBook(bookId, studentId, book);
            return updatedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
