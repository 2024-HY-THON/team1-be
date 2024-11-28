package com.example.teamOnebe.controller;

import com.example.teamOnebe.dto.EmailDto;
import com.example.teamOnebe.dto.EmailVerificationDto;
import com.example.teamOnebe.dto.UserRegisterDto;
import com.example.teamOnebe.dto.UsernameDto;
import com.example.teamOnebe.service.EmailService;
import com.example.teamOnebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/usernameVerify")
    public ResponseEntity<String> usernameVerify(@RequestBody UsernameDto usernameDto)
    {
        if(!userService.usernameVerify(usernameDto.getUsername()))
        {
            return ResponseEntity.status(HttpStatus.OK).body("Available username");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        Map<String, Object> response = new HashMap<>();

        if (userRegisterDto.getUsername() == null || userRegisterDto.getPassword() == null
            || userRegisterDto.getEmail() == null || userRegisterDto.getName() == null)
        {
            response.put("status", "error");
            response.put("message", "Invalid user data");
            return ResponseEntity.badRequest().body(response);
        }
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPasswordVerify()))
        {
            response.put("status", "error");
            response.put("message", "Passwords do not match");
            return ResponseEntity.badRequest().body(response);
        }

        if(!userService.register(userRegisterDto))
        {
            response.put("status", "error");
            response.put("message", "Username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        response.put("status", "success");
        response.put("message", "Registered");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mail/send")
    public ResponseEntity<String> sendVerification(@RequestBody EmailDto emailDto)
    {
        if(emailDto == null || emailDto.getEmail() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        emailService.sendEmail(emailDto.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body("email sent");
    }

    @PostMapping("/mail/verify")
    public ResponseEntity<String> verificationCheck(@RequestBody EmailVerificationDto emailVerificationDto)
    {
        if(emailVerificationDto == null || emailVerificationDto.getEmail() == null || emailVerificationDto.getCode() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        if(emailService.verify(emailVerificationDto))
        {
            return ResponseEntity.status(HttpStatus.OK).body("verification success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("verification failed"); //잘못된 인증번호인경우
    }

}