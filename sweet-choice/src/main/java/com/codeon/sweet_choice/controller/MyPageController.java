package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageService myPageService;

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
}
