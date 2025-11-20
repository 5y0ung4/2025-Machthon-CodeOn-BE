package com.codeon.sweet_choice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    @Column(nullable = false, unique = true, length = 255)
    private String foodName;

    @Column(nullable = false, length = 100)
    private String foodCategoryName;

    @Column(nullable = false, length = 10)
    private float kcal;

    @Column(nullable = false, length = 10)
    private float carbohydrate;

    @Column(nullable = false, length = 10)
    private float protein;

    @Column(nullable = false, length = 10)
    private float fat;

    @Column(nullable = false, length = 10)
    private float totalSugar;

}
