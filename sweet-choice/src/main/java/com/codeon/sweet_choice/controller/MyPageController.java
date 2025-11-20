package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.dto.DailyRecordResponseDto;
import com.codeon.sweet_choice.service.FoodService;
import com.codeon.sweet_choice.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final FoodService foodService;

    //음식에 대한 리포트 보기
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getAllHistory(
            @PathVariable("userId") Long userId){
        try {
            List<AnalysisResponseDto> historyDto = myPageService.getAnalysisHistory(userId);
            return ResponseEntity.ok(historyDto);
        } catch (IllegalArgumentException e) {
            // 사용자 또는 리포트 정보가 없을 때 404 Not Found 응답
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //캘린더 당 기록 보기
    @GetMapping("/record")
    public ResponseEntity<DailyRecordResponseDto> getDailyRecord(
            @RequestParam Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        // 날짜 파라미터가 없으면 오늘 날짜로 설정
        if (date == null) {
            date = LocalDate.now();
        }

        DailyRecordResponseDto response = myPageService.getDailySugarRecord(userId, date);
        return ResponseEntity.ok(response);
    }

    // 섭취 기록 삭제
    // URL 예시: DELETE /api/food/record/15?userId=1
    @DeleteMapping("/record/{recordId}")
    public ResponseEntity<String> deleteFoodRecord(
            @PathVariable Long recordId,
            @RequestParam Long userId
    ) {
        foodService.deleteFoodRecord(userId, recordId);
        return ResponseEntity.ok("섭취 기록이 성공적으로 삭제되었습니다.");
    }
}
