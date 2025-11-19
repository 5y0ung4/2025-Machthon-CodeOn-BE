package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.AnalysisResponseDto;
import com.codeon.sweet_choice.entity.AnalyzeReport;
import com.codeon.sweet_choice.entity.Food;
import com.codeon.sweet_choice.entity.History;
import com.codeon.sweet_choice.entity.User;
import com.codeon.sweet_choice.repository.HistoryRepository;
import com.codeon.sweet_choice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

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
}
