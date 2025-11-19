package com.codeon.sweet_choice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizResultDto {
    private boolean correct;       // 정답 여부
    private String explanation;    // 설명
}