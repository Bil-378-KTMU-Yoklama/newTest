package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentRegister {
    private Integer id;
    private String kod;
    private String name;
    private String surname;
    private String faculty;
    private String department;
}
