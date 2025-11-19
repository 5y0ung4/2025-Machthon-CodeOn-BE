package com.codeon.sweet_choice.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SugarRecordResponseDto {
    private Long recordId;
    private String foodName;
    private int count;
    private double calcSugar; // (음식 당류 * 횟수) 계산된 값
    private LocalDateTime recordDate;

    private List<Detail> details;
    public record Detail(String sugarName, double amount) {}
}