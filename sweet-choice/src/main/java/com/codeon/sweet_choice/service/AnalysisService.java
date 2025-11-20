package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.dto.FoodDetailDto;
import com.codeon.sweet_choice.entity.*;
import com.codeon.sweet_choice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final analysisRepository analysisRepository;
    private final GeminiService geminiService;
    private final HistoryRepository historyRepository;
    private final SugarContainRepository sugarContainRepository;

    public AnalysisResponseDto getAnalysisReport(Long userId, Long foodId){
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new IllegalArgumentException("음식 정보가 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("로그인 해야 합니다.")
        );

        StringBuilder foodPrompt = new StringBuilder();
        foodPrompt.append("음식 정보\n");
        foodPrompt.append("--------------\n");
        foodPrompt.append("음식 이름: ").append(food.getFoodName()).append("\n");
        foodPrompt.append("칼로리: ").append(food.getKcal()).append("\n");
        foodPrompt.append("탄수화물 (g): ").append(food.getCarbohydrate()).append("\n");
        foodPrompt.append("단백질 (g): ").append(food.getProtein()).append("\n");
        foodPrompt.append("지방 (g): ").append(food.getFat()).append("\n");
        foodPrompt.append("총 당류 (g): ").append(food.getTotalSugar()).append("\n");

        List<SugarContain> sugarContains = sugarContainRepository.findAllByFoodId(food);
        foodPrompt.append("음식에 함류된 당 종류\n");
        foodPrompt.append("--------------\n");
        if(sugarContains != null && !sugarContains.isEmpty()){
            for (SugarContain sugarContain : sugarContains) {
                foodPrompt.append(" - ").append(sugarContain.getSugarId().getSugarNameKR()).append(": ")
                        .append(sugarContain.getGram()).append("(g)\n");

            }
        }else{
            foodPrompt.append("알 수 없음\n");
        }

        StringBuilder userPrompt = new StringBuilder();
        userPrompt.append("유저 정보\n");
        userPrompt.append("--------------\n");
        userPrompt.append("키 (cm): ").append(user.getHeight()).append("\n");
        userPrompt.append("몸무게 (kg): ").append(user.getWeight()).append("\n");
        userPrompt.append("성별: ").append(user.getSex()).append("\n");
        userPrompt.append("사용자 상태: ");
        if (user.getState().equals("A")) {
            userPrompt.append("건강 유지 및 다이어터").append("\n");
        }else if (user.getState().equals("B")) {
            userPrompt.append("혈당 관리 및 전단계").append("\n");
        }else if (user.getState().equals("C")) {
            userPrompt.append("당뇨 환자").append("\n");
        }else{
            userPrompt.append("일반인").append("\n");
        }
        userPrompt.append("총 당류 섭취 허용량(ADI): ").append(user.getAdi()).append("\n");

        String prompt = "유저 정보를 바탕으로, 해당 음식이 건강에 어떤 영향을 미칠지 알려 줘. 섭취를 추천하는지, 아니라면 이유는 무엇인지 등을 친절하게 알려 줘. 글자는 500자 이상 800자 이하로 줄여 줘";

        String report = geminiService.analysisReport(userPrompt.toString() + foodPrompt.toString() + prompt);

        AnalyzeReport analyzeReport =
                analysisRepository.findByUserIdAndFoodId(user, food);

        if (analyzeReport != null) {
            return new AnalysisResponseDto(
                    food.getFoodId(),
                    analyzeReport.getReportId(),
                    analyzeReport.getContent()
            );
        }else{
            analyzeReport = new AnalyzeReport();
            analyzeReport.setFoodId(food);
            analyzeReport.setUserId(user);
            analyzeReport.setContent(report);
        }

        analysisRepository.save(analyzeReport);

        return new AnalysisResponseDto(food.getFoodId(), analyzeReport.getReportId(), report);

    }

    @Transactional
    public String scrapReport(Long userId, Long reportId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        AnalyzeReport report = analysisRepository.findByReportId(reportId);
        if (report == null) {
            throw new IllegalArgumentException("리포트를 찾을 수 없습니다.");
        }

        boolean alreadyScrapped = historyRepository.existsByUserIdAndReportId(user, report);
        if (alreadyScrapped) {
            historyRepository.deleteByUserIdAndReportId(user, report);
            return "스크랩 취소";

        }else{
            History history = new History();
            history.setUserId(user);
            history.setReportId(report);

            historyRepository.save(history);
            return "스크랩 완료";
        }
    }
}
