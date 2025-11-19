package com.codeon.sweet_choice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AnalysisResponseDto {
    private Long foodId;
    private Long reportId;
    private String content;
}
