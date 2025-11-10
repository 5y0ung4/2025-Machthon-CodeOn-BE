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

    @OneToOne(mappedBy = "sugar", fetch = FetchType.LAZY)
    private InformationPost informationPost;
}