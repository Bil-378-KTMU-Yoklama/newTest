package com.example.newTest.dto;

import com.example.newTest.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAndStatus {
    private String token;
    private Role status;
    private Integer id;
}
