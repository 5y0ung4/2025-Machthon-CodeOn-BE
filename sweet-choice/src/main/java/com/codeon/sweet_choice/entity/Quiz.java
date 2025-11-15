package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @Column(nullable = false, length = 50)
    private String question;

    @Column(nullable = false)
    private boolean answer;

    @Column(nullable = false, length = 250)
    private String explanation;
}
