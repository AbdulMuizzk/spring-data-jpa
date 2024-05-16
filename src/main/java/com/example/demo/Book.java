package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "student_books")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long Id;


    private String bookName;

    @Column(name="created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="student_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name="student_book_fk"))
    private Student student;


}
