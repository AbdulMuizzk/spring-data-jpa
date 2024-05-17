package com.example.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EnrolmentDto {
    private Long studentId;
    private Long courseId;
    private LocalDateTime createdAt;
}
