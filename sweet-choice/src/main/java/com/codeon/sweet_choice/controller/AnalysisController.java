package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.config.CustomUserDetails;
import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.dto.FoodDetailDto;
import com.codeon.sweet_choice.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping("/report")
    public ResponseEntity<AnalysisResponseDto> getAnalysisReport(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Long foodId) {
        Long userId = userDetails.getUserId();
        AnalysisResponseDto analysisResponseDto = analysisService.getAnalysisReport(userId, foodId);
        return ResponseEntity.ok(analysisResponseDto);
    }

    @PatchMapping("/scrap")
    public void scrapReport(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Long reportId){
        Long userId = userDetails.getUserId();
        analysisService.scrapReport(userId, reportId);
    }
}
