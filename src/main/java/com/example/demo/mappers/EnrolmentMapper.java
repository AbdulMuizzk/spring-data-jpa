package com.example.demo.mappers;

import com.example.demo.dtos.EnrolmentDto;
import com.example.demo.entities.Enrolment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrolmentMapper {
    EnrolmentDto toEnrolmentDto(Enrolment enrolment);
}
