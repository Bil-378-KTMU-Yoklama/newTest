package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoSaveDto {
    private String username;
    private String password;
    private String status;
}
