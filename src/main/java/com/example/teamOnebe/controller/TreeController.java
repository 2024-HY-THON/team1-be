package com.example.teamOnebe.controller;

import com.example.teamOnebe.service.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping("/tree/info")
    public ResponseEntity<Map<String, Object>> treeInfo(Principal principal)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("exp", treeService.getTreeDiaryNum(principal.getName()));
        return ResponseEntity.ok(response);
    }
}