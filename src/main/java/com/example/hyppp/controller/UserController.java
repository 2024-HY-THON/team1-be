package com.example.hyppp.controller;

import com.example.hyppp.dto.UserRegisterDto;
import com.example.hyppp.service.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        Map<String, Object> response = new HashMap<>();

        if (userRegisterDto.getUsername() == null || userRegisterDto.getPassword() == null) {
            response.put("status", "error");
            response.put("message", "Invalid user data");
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

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto userLoginDto)
//    {
//
//    }

    @GetMapping("/")
    public String hello(Principal principal)
    {
        if(principal != null) System.out.println(principal.getName());
        return "hello";
    }

    @GetMapping("/user")
    public String user(Principal principal)
    {
        System.out.println(principal.getName()+"uuu");
        return "you are user";
    }
}
