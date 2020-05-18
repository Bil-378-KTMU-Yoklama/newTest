package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private Integer id;
    @Column(name = "coach_kod")
    private String kod;
    @Column (name = "coach_name")
    private String name;
    @Column (name = "coach_surname")
    private String surname;
    @OneToOne
    @JoinColumn (name = "user_info")
    private UserInfo userInfo;
}
