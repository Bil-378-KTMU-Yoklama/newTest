package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonIdandKod {
    private Integer id;
    private String kod;
    private Boolean status;
}
