package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "`user`")
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

    private String firstName;
    private String lastName;

    private LocalDateTime createdAt;

    @Column(length = 1)
    private String state;

    private Integer height;

    private Integer weight;

    @Column(length = 1)
    private String sex;

    @Column(nullable = true)
    private Integer adi;

    public User(String nickname, String email, String password, String firstName, String lastName, LocalDateTime createdAt, Integer adi) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.adi = adi;
    }

}
