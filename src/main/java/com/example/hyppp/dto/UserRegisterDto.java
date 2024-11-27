package com.example.hyppp.dto;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
    private String name;
}
