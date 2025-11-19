package com.codeon.sweet_choice.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DailyRecordResponseDto {
    private int sugarADI;
    private double totalConsumedSugar; // 오늘 먹은 당 총합
    private double remainSugar;        // 남은 허용량 (ADI - consumed)
    private int percent;               // 섭취 퍼센트

    private List<SugarRecordResponseDto> records;
    public record Detail(String sugarNameKR, double amount) {}
}