package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.*;
import com.codeon.sweet_choice.entity.*;
import com.codeon.sweet_choice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;
    private final SugarContainRepository sugarContainRepository;
    private final UserRepository userRepository;
    private final SugarRecordRepository sugarRecordRepository;

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

    // 검색어를 포함하는 음식 전체 조회(당 종류로 검색)
    public List<FoodListDto> getFoodListBySugar(String search) {
        return sugarContainRepository.findFoodsBySugarName(search)
                .stream()
                .map(f -> new FoodListDto(f.getFoodId(), f.getFoodName()))
                .toList();
    }

    // 음식 분류로 음식 리스트 조회
    public List<FoodListDto> getFoodListByCategory(String search) {
        List<FoodListDto> foodListDtos = new ArrayList<>();
        List<Food> foodList = foodRepository.findByFoodCategoryName(search);
        for (Food food : foodList) {
            FoodListDto foodDto = new FoodListDto(food.getFoodId(), food.getFoodName());
            foodListDtos.add(foodDto);
        }
        return foodListDtos;
    }

    // 음식 아이디로 음식 정보 상세 조회
    public FoodDetailDto getFoodDetailByFoodId(Long foodId) {
        Food food = foodRepository.findByFoodId(foodId);
        List<SugarContain> sugarContains = sugarContainRepository.findByFoodId(food);

        FoodDetailDto dto = new FoodDetailDto();
        dto.setFoodId(food.getFoodId());
        dto.setFoodCategoryName(food.getFoodCategoryName());
        dto.setFoodName(food.getFoodName());
        dto.setKcal(food.getKcal());
        dto.setCarbohydrate(food.getCarbohydrate());
        dto.setProtein(food.getProtein());
        dto.setFat(food.getFat());
        dto.setTotalSugar(food.getTotalSugar());
        dto.setSugarContains(sugarContains);

        return dto;
    }

    //섭취 음식 저장
    public SugarRecordResponseDto saveFoodRecord(Long userId, Long foodId, int count) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("음식 없음"));

        SugarRecord sugarRecord = SugarRecord.builder()
                .user(user)
                .food(food)
                .recordCount(count)
                .recordDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        SugarRecord saved = sugarRecordRepository.save(sugarRecord);

        return SugarRecordResponseDto.builder()
                .recordId(saved.getRecordId())
                .foodName(food.getFoodName())
                .count(saved.getRecordCount())
                .calcSugar((double) (food.getTotalSugar() * saved.getRecordCount()))
                .recordDate(saved.getRecordDate())
                .build();
    }



    // 섭취 기록 삭제
    public void deleteFoodRecord(Long userId, Long recordId) {
        SugarRecord record = sugarRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록이 존재하지 않습니다."));

        if (!record.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인의 섭취 기록만 삭제할 수 있습니다.");
        }

        sugarRecordRepository.delete(record);
    }

}
