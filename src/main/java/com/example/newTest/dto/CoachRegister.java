package com.example.newTest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoachRegister {
    private String kod;
    private String name;
    private String surname;
    private String login;
    private String password;
}
