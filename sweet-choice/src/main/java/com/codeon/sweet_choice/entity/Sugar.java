package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sugar")
public class Sugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sugarId;

    @Column(name = "sugar_name_kr", nullable = false, unique = true)
    private String sugarNameKR;

    @Column(name = "sugar_name_en", nullable = false, unique = true)
    private String sugarNameEN;

    @OneToOne(mappedBy = "sugar")
    private InformationPost informationPost;
}