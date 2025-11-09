package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 100)
    private String password;

    private LocalDateTime createdAt;

    @Column(length = 1)
    private String state;

    private Integer height;

    private Integer weight;

    @Column(length = 1)
    private String sex;


}
