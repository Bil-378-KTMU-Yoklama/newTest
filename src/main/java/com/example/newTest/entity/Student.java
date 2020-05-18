package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "student_id")
    private Integer id;
    @Column (name = "student_kod")
    private String kod;
    @Column (name = "student_name")
    private String name;
    @Column (name = "student_surname")
    private String surname;
    @Column (name = "student_faculty")
    private String faculty;
    @Column (name = "student_department")
    private String department;
    @OneToOne
    @JoinColumn (name = "user_id")
    private UserInfo userId;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
}
