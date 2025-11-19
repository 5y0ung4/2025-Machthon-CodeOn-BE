package com.codeon.sweet_choice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodRecordRequestDto {
    private Long userId;
    private Long foodId;
    private int count; // 섭취 횟수 (default 1)
}