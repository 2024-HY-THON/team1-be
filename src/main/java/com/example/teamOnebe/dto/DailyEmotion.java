package com.example.teamOnebe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyEmotion {
    private int day;
    private String emotion;
}
