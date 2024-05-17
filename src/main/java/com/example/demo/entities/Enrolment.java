package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Enrolment {
    @EmbeddedId
    private EnrolmentId enrolmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name="student_id", foreignKey = @ForeignKey(name="enrolment_student_id_fk"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name="course_id", foreignKey = @ForeignKey(name="enrolment_course_id_fk"))
    private Course course;

    @Column(name="created_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Long getCourseId() {
        return this.enrolmentId.getCourseId();
    }
    public Long getStudentId(){
        return this.enrolmentId.getStudentId();
    }
}
