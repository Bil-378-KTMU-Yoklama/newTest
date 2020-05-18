package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "lesson_id")
    private Integer id;
    @Column (name = "lesson_kod")
    private String kod;
    @Column (name = "lesson_faculty")
    private String faculty;
    @Column (name = "lesson_department")
    private String department;
    @Column (name = "lesson_week")
    private Integer week;
    @Column (name = "lesson_status")
    private Boolean status;
    @ManyToOne
    @JoinColumn (name = "coach_id")
    private Coach coachId;
}
