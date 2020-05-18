package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Katilim {
    private String kod;
    private String faculty;
    private String department;
    private Boolean status;

}
