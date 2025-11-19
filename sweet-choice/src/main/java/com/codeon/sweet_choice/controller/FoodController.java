package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.FoodDetailDto;
import com.codeon.sweet_choice.dto.FoodListDto;
import com.codeon.sweet_choice.dto.FoodRecordRequestDto;
import com.codeon.sweet_choice.dto.SugarRecordResponseDto;
import com.codeon.sweet_choice.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;

    //음식 이름으로 검색
    @GetMapping("/search/food")
    public ResponseEntity<List<FoodListDto>> getFoodListByFoodId(String search) {
        List<FoodListDto> foodListDto = foodService.getFoodListByFoodName(search);
        return new ResponseEntity<>(foodListDto, HttpStatus.OK) ;
    }

    //당 종류로 검색
    @GetMapping("/search/sugar")
    public ResponseEntity<List<FoodListDto>> getFoodListBySugar(String search) {
        List<FoodListDto> foodListDto = foodService.getFoodListBySugar(search);
        return new ResponseEntity<>(foodListDto, HttpStatus.OK) ;
    }

    //음식 상세정보 조회
    @GetMapping("/search/detail")
    public ResponseEntity<FoodDetailDto> getFoodDetailByFoodId(Long foodId) {
        FoodDetailDto dto = foodService.getFoodDetailByFoodId(foodId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //섭취 기록 저장
    @PostMapping("/record")
    public ResponseEntity<SugarRecordResponseDto> saveFoodRecord(@RequestBody FoodRecordRequestDto request) {
        SugarRecordResponseDto response = foodService.saveFoodRecord(
                request.getUserId(),
                request.getFoodId(),
                request.getCount()
        );
        return ResponseEntity.ok(response);
    }

}
