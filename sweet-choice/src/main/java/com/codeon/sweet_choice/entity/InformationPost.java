package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "InformationPost")
public class InformationPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sugarId", nullable = false)
    private Sugar sugar;

    @Column(columnDefinition = "varchar(255)")
    private String content;

    private Integer kcal;

    @Column(columnDefinition = "varchar(100)")
    private String bloodSugar;

    @Column(columnDefinition = "varchar(100)")
    private String sweet;

    @Column(columnDefinition = "varchar(255)")
    private String characteristic;

    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}