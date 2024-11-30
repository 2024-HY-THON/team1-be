package com.example.teamOnebe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicDto {
    private int music_id;
    private String title;
    private String artist;
}
