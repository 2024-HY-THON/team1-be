package com.example.teamOnebe.dto;

import com.example.teamOnebe.entity.Diary;
import lombok.Data;

@Data
public class TodayDiaryDto {
    private String emotion;
    private String type;
    private String content;

    public TodayDiaryDto(Diary diary)
    {
        emotion = diary.getEmotion();
        type = diary.getType();
        content = diary.getContent();
    }
}