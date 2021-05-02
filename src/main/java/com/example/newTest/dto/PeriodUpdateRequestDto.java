package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodUpdateRequestDto {
    private String purpose;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
