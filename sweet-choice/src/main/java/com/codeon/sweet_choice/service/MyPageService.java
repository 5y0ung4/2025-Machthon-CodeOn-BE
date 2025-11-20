package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.dto.DailyRecordResponseDto;
import com.codeon.sweet_choice.dto.SugarRecordResponseDto;
import com.codeon.sweet_choice.entity.*;
import com.codeon.sweet_choice.repository.HistoryRepository;
import com.codeon.sweet_choice.repository.SugarContainRepository;
import com.codeon.sweet_choice.repository.SugarRecordRepository;
import com.codeon.sweet_choice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final SugarRecordRepository sugarRecordRepository;
    private final SugarContainRepository sugarContainRepository;

    //특정 유저가 스크랩한 모든 리포트 조회
    public List<AnalysisResponseDto> getAnalysisHistory (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("로그인시 이용가능합니다."));

        List<History> historyList = historyRepository.findAllByUserId(user);

        return historyList.stream().map(history-> {
            AnalyzeReport report = history.getReportId();
            Food food  = report.getFoodId();

            return new AnalysisResponseDto(
                    report.getReportId(),
                    food.getFoodId(),
                    report.getContent()
            );
        }).collect(Collectors.toList());

    }

    //특정 날짜의 당 기록 보기
    @Transactional(readOnly = true)
    public DailyRecordResponseDto getDailySugarRecord(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<SugarRecord> records = sugarRecordRepository.findAllByUserAndRecordDateBetween(user, startOfDay, endOfDay);

        double totalConsumed = 0.0;
        List<SugarRecordResponseDto> recordDtos = new ArrayList<>();

        for (SugarRecord record : records) {
            Food food = record.getFood();
            int count = record.getRecordCount();

            double rawTotal = food.getTotalSugar() * count;
            double calcTotal = Math.round(rawTotal * 100.0) / 100.0;
            totalConsumed += calcTotal;

            // 내부 레코드 리스트 생성
            List<DailyRecordResponseDto.Detail> details = getSugarDetails(food, count);
            recordDtos.add(SugarRecordResponseDto.builder()
                    .recordDate(record.getRecordDate()) // 날짜 넣기
                    .details(details)                   // 상세정보 넣기
                    // .foodName(...) -> 안 넣음 (Null -> 숨겨짐)
                    // .calcSugar(...) -> 안 넣음 (Null -> 숨겨짐)
                    .build());
        }

        // ADI 계산 (기존 로직)
        Integer userAdi = user.getAdi();
        int adi = (userAdi == null) ? 0 : userAdi;

        double finalTotalConsumed = Math.round(totalConsumed * 100.0) / 100.0;
        double rawRemain = adi - finalTotalConsumed;
        double finalRemain = Math.round(rawRemain * 100.0) / 100.0;

        int percent = (adi > 0) ? (int) ((finalTotalConsumed / adi) * 100) : 0;

        return DailyRecordResponseDto.builder()
                .sugarADI(adi)
                .totalConsumedSugar(finalTotalConsumed)
                .remainSugar(finalRemain)
                .percent(percent)
                .records(recordDtos)
                .build();
    }

    // [Helper 메서드] 상세 당 정보 생성기
    private List<DailyRecordResponseDto.Detail> getSugarDetails(Food food, int count) {
        List<SugarContain> contains = sugarContainRepository.findByFoodId(food);
        List<DailyRecordResponseDto.Detail> details = new ArrayList<>();

        for (SugarContain sc : contains) {
            double rawAmount = sc.getGram() * count;
            double roundedAmount = Math.round(rawAmount * 100.0) / 100.0;

            // record의 변수명이 sugarNameKR 이므로 순서에 맞춰 주입
            details.add(new DailyRecordResponseDto.Detail(
                    sc.getSugarId().getSugarNameKR(), // 한글 이름
                    roundedAmount
            ));
        }
        return details;
    }

}
