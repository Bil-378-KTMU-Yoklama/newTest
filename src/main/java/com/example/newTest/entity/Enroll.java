package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "enroll_id")
    private Integer id;
    @ManyToOne
    @JoinColumn (name = "student_id")
    private Student studentId;
    @ManyToOne
    @JoinColumn (name = "lesson_id")
    private Lesson lessonId;
}
