package com.example.newTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Integer id;
    @Column (name = "period_purpose")
    private String purpose;
    @Column (name = "period_start_datetime")
    private ZonedDateTime startDateTime;
    @Column (name = "period_end_datetime")
    private ZonedDateTime endDateTime;
    @Column (name = "period_edited_datetime")
    private ZonedDateTime editedDateTime;
    @ManyToOne
    @JoinColumn (name = "user_info_id")
    private UserInfo userInfo;
}
