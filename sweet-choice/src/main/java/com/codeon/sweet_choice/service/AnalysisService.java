package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private GeminiService geminiService;
    private FoodService foodService;
    private UserService userService;

    private Integer sugarRecommendation;

    public String getAnalysisReport(String prompt, User user){
        String analysis = geminiService.analysisReport(prompt);
    }
}
