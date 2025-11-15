package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.QuizDto;
import com.codeon.sweet_choice.dto.QuizResultDto;
import com.codeon.sweet_choice.entity.Quiz;
import com.codeon.sweet_choice.repository.QuizRepository;
import com.codeon.sweet_choice.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizRepository quizRepository;

    @GetMapping
    public ResponseEntity<List<QuizDto>> getQuizzes(){
        return ResponseEntity.ok(quizService.getQuizShuffle());
    }

    @GetMapping("/{quizId}/checkAnswer")
    public ResponseEntity<QuizResultDto> checkAnswer(
            @PathVariable Long quizId,
            @RequestParam boolean userAnswer) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈 없음"));

        boolean result = quizService.checkAnswer(quizId, userAnswer);

        QuizResultDto response = new QuizResultDto(
                result,
                quiz.getExplanation()
        );

        return ResponseEntity.ok(response);
    }


}
