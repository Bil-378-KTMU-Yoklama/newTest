package com.example.newTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAndStatus {
    private String token;
    private String status;
    private Integer id;
}
