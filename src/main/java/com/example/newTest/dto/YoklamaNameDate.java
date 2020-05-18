package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YoklamaNameDate {
    private String name;
    private Date date;
    private Integer lessonId;
    private List<Integer> studentId;
}
