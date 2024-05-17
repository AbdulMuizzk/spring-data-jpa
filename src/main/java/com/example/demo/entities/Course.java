package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @SequenceGenerator(name =
    "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    private Long id;

    @Column(name = "course_name", nullable = false, columnDefinition = "TEXT")
    private String courseName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String department;


}
