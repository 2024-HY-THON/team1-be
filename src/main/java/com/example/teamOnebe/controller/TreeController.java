package com.example.teamOnebe.controller;

import com.example.teamOnebe.dto.MusicDto;
import com.example.teamOnebe.entity.Music;
import com.example.teamOnebe.service.MusicService;
import com.example.teamOnebe.service.ShopService;
import com.example.teamOnebe.service.TreeService;
import com.example.teamOnebe.service.UserService;
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
    private final UserService userService;
    private final MusicService musicService;

    @GetMapping("/tree/info")
    public ResponseEntity<Map<String, Object>> treeInfo(Principal principal)
    {
        Map<String, Object> response = new HashMap<>();
        int exp = treeService.getTreeDiaryNum(principal.getName());
        response.put("exp", exp);
        response.put("level",1 + exp/10);
        response.put("wear", shopService.getWearNum(principal.getName()));
        response.put("name", userService.getName(principal.getName()));

        MusicDto musicDto = musicService.getMusic(principal.getName());
        if(musicDto!=null)
        {
            response.put("title", musicDto.getTitle());
            response.put("artist",musicDto.getArtist());
            response.put("music_id", musicDto.getMusic_id());
        }
        else
        {
            response.put("title", "음악제목을 찾을 수 없습니다.");
            response.put("artist","가수정보를 찾을 수 없습니다.");
            response.put("music_id", 0);
        }

        return ResponseEntity.ok(response);
    }
}