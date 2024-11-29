package com.example.teamOnebe.controller;

import com.example.teamOnebe.dto.DailyEmotion;
import com.example.teamOnebe.dto.DiarySaveDto;
import com.example.teamOnebe.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/diary/save")
    public ResponseEntity<String> save(@RequestBody DiarySaveDto dto, Principal principal)
    {
        if(dto == null || dto.getType() == null || dto.getEmotion() == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        if(diaryService.save(dto, principal.getName()))
        {
            return ResponseEntity.status(HttpStatus.OK).body("Diary saved");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tree is full or something went wrong");
    }

    @GetMapping("/emotions/{year}/{month}")
    public ResponseEntity<?> emotions(Principal principal, @PathVariable("year") Long year, @PathVariable("month") Long month) {
        List<DailyEmotion> emotions = diaryService.getEmotions(principal.getName());

        if (emotions == null) {
            // emotions가 null => user 없음
            return ResponseEntity.badRequest().body("User not found");
        } else {
            // emotions가 존재하면 200 OK와 함께 데이터 반환
            return ResponseEntity.ok(emotions);
        }
    }
}
