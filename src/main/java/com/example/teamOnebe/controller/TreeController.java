package com.example.teamOnebe.controller;

import com.example.teamOnebe.service.ShopService;
import com.example.teamOnebe.service.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;
    private final ShopService shopService;

    @GetMapping("/tree/info")
    public ResponseEntity<Map<String, Object>> treeInfo(Principal principal)
    {
        Map<String, Object> response = new HashMap<>();
        int exp = treeService.getTreeDiaryNum(principal.getName());
        response.put("exp", exp);
        response.put("level", exp/10);
        response.put("wear", shopService.getWearNum(principal.getName()));
        return ResponseEntity.ok(response);
    }
}