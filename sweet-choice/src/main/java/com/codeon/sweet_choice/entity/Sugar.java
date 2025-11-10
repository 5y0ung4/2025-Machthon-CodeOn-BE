package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sugar")
public class Sugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sugarId;

    @Column(nullable = false, unique = true)
    private String sugarName;

    // InformationPost와 1:1 관계 (InfoPost가 Sugar를 참조)
    @OneToOne(mappedBy = "sugar", fetch = FetchType.LAZY)
    private InformationPost informationPost;
}