package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping("/find-all")
    public List<Book> getBooks() {
        return bookService.findAll();
    }
    @GetMapping("/find-by-book-id")
    public Book getBook(@RequestParam Long bookId) {
        return bookService.findById(bookId);
    }
    @GetMapping("/find-by-student-id")
    public ResponseEntity<Book> getBookByStudentId(@RequestParam Long studentId) {
        if (studentId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Book> bookFound =  bookService.findBookByStudentId(studentId);
        return bookFound.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/delete-book-by-student-id")
    public boolean deleteBookByStudentId(@RequestParam Long studentId) {
         return bookService.deleteBookByStudentId(studentId);
    }
    @PostMapping("/add-a-new-book")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book, @RequestParam Long studentId) {
        if (studentId == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Student> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            Optional<Book> newBook = bookService.addNewBook(book, student.get());
            return newBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
