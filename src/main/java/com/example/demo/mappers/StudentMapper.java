package com.example.demo.mappers;

import com.example.demo.dtos.StudentDto;
import com.example.demo.entities.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toStudentDto(Student student);
}
