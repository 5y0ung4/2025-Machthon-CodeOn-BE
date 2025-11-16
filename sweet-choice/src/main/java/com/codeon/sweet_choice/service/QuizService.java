package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.QuizDto;
import com.codeon.sweet_choice.entity.Quiz;
import com.codeon.sweet_choice.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
public class QuizService {

    private final QuizRepository quizRepository;

    public List<QuizDto> getQuizShuffle(){
        List<QuizDto> quizzes = quizRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        Collections.shuffle(quizzes);

        return quizzes;
    }

    public boolean checkAnswer(Long quizId, boolean userAnswer){
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(()->new EntityNotFoundException("퀴즈를 찾을 수 없습니다.: " + quizId));

        return userAnswer == quiz.isAnswer();
    }

    private QuizDto convertToDto(Quiz quiz){
        return QuizDto.builder()
                .quizId(quiz.getQuizId())
                .question(quiz.getQuestion())
                .answer(quiz.isAnswer())
                .explanation(quiz.getExplanation())
                .build();
    }
}
