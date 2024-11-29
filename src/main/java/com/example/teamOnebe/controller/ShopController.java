package com.example.teamOnebe.controller;

import com.example.teamOnebe.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/wear/{num}")
    public ResponseEntity<?> wear(@PathVariable int num, Principal principal)
    {
        int wearNum = shopService.getWearNum(principal.getName());
        return ResponseEntity.ok(wearNum);
    }
}