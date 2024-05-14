package com.example.demo;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        @UniqueConstraint(name="student_id_card_number_unique", columnNames = "card_number")
})
@Setter
@Getter
@NoArgsConstructor
@ToString
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_card_id_sequence")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "card_number", nullable = false, length = 10)
    private String cardNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "student_id_fk"))
    private Student student;


}