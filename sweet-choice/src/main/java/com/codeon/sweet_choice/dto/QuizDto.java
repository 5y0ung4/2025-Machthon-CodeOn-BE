package com.codeon.sweet_choice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class QuizDto {
    private Long quizId;
    private String question;
    private boolean answer;
    private String explanation;
}
