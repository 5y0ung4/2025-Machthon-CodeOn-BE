package com.codeon.sweet_choice.dto;

import com.codeon.sweet_choice.entity.Food;
import com.codeon.sweet_choice.entity.SugarContain;
import com.codeon.sweet_choice.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class RecordAllDto {

    private String foodName;
    private String foodCategoryName;
    private float totalKcal;
    private float totalCarbohydrate;
    private float totalProtein;
    private float totalFat;
    private float totalSugar;
    private List<SugarContainDto> sugarContains;

    private LocalDateTime recordDate;
    private Integer recordCount;

}
