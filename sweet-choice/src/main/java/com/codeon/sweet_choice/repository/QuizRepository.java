package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findByQuizId(Long quizId);
}
