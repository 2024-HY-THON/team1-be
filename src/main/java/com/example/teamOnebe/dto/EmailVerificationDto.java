package com.example.teamOnebe.dto;

import lombok.Data;

@Data
public class EmailVerificationDto {
    private String email;
    private Long code;
}
