package com.codeon.sweet_choice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sugarContain")
public class SugarContain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long containId;

    @OneToOne
    @JsonIgnore // 순환참조 방지
    @JoinColumn(name = "foodId")
    private Food foodId;

    @ManyToOne
    @JoinColumn(name = "sugarId")
    private Sugar sugarId;

    private float gram;

    private float percent;


}
