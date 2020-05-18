package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Yoklama {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yoklama_id")
    private Integer id;
    @Column(name = "yoklama_name")
    private String name;
    @Column(name = "yoklama_date")
    private Date date;
    @Column (name = "yoklama_status")
    private Boolean status;
    @ManyToOne
    @JoinColumn (name = "enroll_id")
    private Enroll enrollId;
}
