package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.FoodDetailDto;
import com.codeon.sweet_choice.dto.FoodListDto;
import com.codeon.sweet_choice.entity.Food;
import com.codeon.sweet_choice.entity.SugarContain;
import com.codeon.sweet_choice.repository.FoodRepository;
import com.codeon.sweet_choice.repository.SugarContainRepository;
import com.codeon.sweet_choice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;
    private final SugarContainRepository sugarContainRepository;

    // 검색어를 포함하는 음식 전체 조회(음식 이름으로 검색)
    public List<FoodListDto> getFoodListByFoodName(String search) {
        List<FoodListDto> foodListDtos = new ArrayList<>();
        List<Food> foodList = foodRepository.findByFoodNameContaining(search);
        for (Food food : foodList) {
            FoodListDto foodDto = new FoodListDto(food.getFoodId(), food.getFoodName());
            foodListDtos.add(foodDto);
        }
        return foodListDtos;
    }

    //검색어를 포함하는 음식 전체 조회(당 종류로 검색)
    public List<FoodListDto> getFoodListBySugar(String search) {
        return sugarContainRepository.findFoodsBySugarName(search)
                .stream()
                .map(f -> new FoodListDto(f.getFoodId(), f.getFoodName()))
                .toList();
    }

    public FoodDetailDto getFoodDetailByFoodId(Long foodId) {
        Food food = foodRepository.findByFoodId(foodId);
        List<SugarContain> sugarContains = sugarContainRepository.findByFoodId(food);

        FoodDetailDto dto = new FoodDetailDto();
        dto.setFoodId(food.getFoodId());
        dto.setFoodName(food.getFoodName());
        dto.setKcal(food.getKcal());
        dto.setCarbohydrate(food.getCarbohydrate());
        dto.setProtein(food.getProtein());
        dto.setFat(food.getFat());
        dto.setTotalSugar(food.getTotalSugar());
        dto.setSugarContains(sugarContains);

        return dto;
    }
}
