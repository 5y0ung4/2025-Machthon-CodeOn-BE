package com.codeon.sweet_choice.dto;

import com.codeon.sweet_choice.entity.SugarContain;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class FoodDetailDto {
    private Long foodId;
    private String foodCategoryName;
    private String foodName;
    private double kcal;
    private double carbohydrate;
    private double protein;
    private double fat;
    private double totalSugar;

    private List<SugarContain> sugarContains;

}
