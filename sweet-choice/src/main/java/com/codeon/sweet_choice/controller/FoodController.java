package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.config.CustomUserDetails;
import com.codeon.sweet_choice.dto.FoodDetailDto;
import com.codeon.sweet_choice.dto.FoodListDto;
import com.codeon.sweet_choice.dto.FoodRecordRequestDto;
import com.codeon.sweet_choice.dto.SugarRecordResponseDto;
import com.codeon.sweet_choice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    //음식 이름으로 검색
    @GetMapping("/search/food")
    public ResponseEntity<List<FoodListDto>> getFoodListByFoodId(@RequestParam String search) {
        List<FoodListDto> foodListDto = foodService.getFoodListByFoodName(search);
        return new ResponseEntity<>(foodListDto, HttpStatus.OK) ;
    }

    //당 종류로 검색
    @GetMapping("/search/sugar")
    public ResponseEntity<List<FoodListDto>> getFoodListBySugar(@RequestParam String search) {
        List<FoodListDto> foodListDto = foodService.getFoodListBySugar(search);
        return new ResponseEntity<>(foodListDto, HttpStatus.OK) ;
    }

    //음식 상세정보 조회
    @GetMapping("/search/detail")
    public ResponseEntity<FoodDetailDto> getFoodDetailByFoodId(@RequestParam Long foodId) {
        FoodDetailDto dto = foodService.getFoodDetailByFoodId(foodId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // 음식 분류명으로 리스트 조회
    @GetMapping("/search/category")
    public ResponseEntity<List<FoodListDto>> getFoodListByCategory(@RequestParam String search) {
        List<FoodListDto> foodListDto = foodService.getFoodListByCategory(search);
        return new ResponseEntity<>(foodListDto, HttpStatus.OK);
    }


    //섭취 기록 저장
    @PostMapping("/record")
    public ResponseEntity<SugarRecordResponseDto> saveFoodRecord(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody FoodRecordRequestDto request) {
        Long userId = userDetails.getUserId();
        SugarRecordResponseDto response = foodService.saveFoodRecord(
                userId,
                request.getFoodId(),
                request.getCount()
        );
        return ResponseEntity.ok(response);
    }

}
