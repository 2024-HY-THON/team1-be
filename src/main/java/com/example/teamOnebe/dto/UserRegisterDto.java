package com.example.teamOnebe.dto;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String passwordVerify;
    private String email;
    private String name;
}
