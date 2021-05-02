package com.example.newTest.dto;

import com.example.newTest.entity.StateOfEnroll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonForEnrollmentResponseDto {
    private Integer enrollId;
    private Integer lessonId;
    private String lessonKod;
    private Integer lessonSemester;
    private StateOfEnroll enrollState;
}
