package com.example.teamOnebe.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamOnebe.dto.UpdateAddressDto;
import com.example.teamOnebe.dto.UpdateNameDto;
import com.example.teamOnebe.dto.UpdatePasswordDto;
import com.example.teamOnebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;

    // 이름 변경
    @PatchMapping("/updateName")
    public ResponseEntity<Map<String, Object>> updateName(@RequestBody UpdateNameDto updateNameDto, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        String currentUsername = principal.getName();

        if (userService.UpdateName(currentUsername, updateNameDto.getNewName())) {
            response.put("status","success");
            response.put("message", "Name updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to update name");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 비밀번호 변경
    @PatchMapping("/updatePassword")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        String currentUsername = principal.getName();

        if (userService.UpdatePassword(currentUsername, updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword())) {
            response.put("status", "success");
            response.put("message", "Password updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to update password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // 주소 변경
    @PatchMapping("/updateAddress")
    public ResponseEntity<Map<String, Object>> updateAddress(@RequestBody UpdateAddressDto updateAddressDto, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        String currentUsername = principal.getName();

        if (userService.UpdateAddress(currentUsername, updateAddressDto.getNewAddress())) {
            response.put("status", "success");
            response.put("message", "Address updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to update address");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}