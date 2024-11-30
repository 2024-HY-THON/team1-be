package com.example.teamOnebe.controller;

import com.example.teamOnebe.dto.MusicDto;
import com.example.teamOnebe.entity.Music;
import com.example.teamOnebe.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping("music/set")
    public ResponseEntity<?> setMusic(@RequestBody MusicDto musicDto, Principal principal)
    {
        if(musicService.setMusic(principal.getName(), musicDto))
        {
            return ResponseEntity.ok("Music changed");
        }
        return ResponseEntity.notFound().build();
    }
}
