package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonRegister {
    private String kod;
    private String faculty;
    private String department;
    private Integer week;
    private Boolean status;
    private Integer coach_id;
    private List<Integer> students;
}
