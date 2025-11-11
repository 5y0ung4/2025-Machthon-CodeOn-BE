package com.codeon.sweet_choice.dto;

import com.codeon.sweet_choice.entity.InformationPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {

    private Long postId;
    private String sugarNameKR;
    private String sugarNameEN;
    private String content;
    private Integer kcal;
    private String bloodSugar;
    private String sweet;
    private String characteristic;

    public PostResponseDto(InformationPost post) {
        this.postId = post.getPostId();
        this.sugarNameKR = post.getSugar().getSugarNameKR();
        this.sugarNameEN = post.getSugar().getSugarNameEN();
        this.content = post.getContent();
        this.kcal = post.getKcal();
        this.bloodSugar = post.getBloodSugar();
        this.sweet = post.getSweet();
        this.characteristic = post.getCharacteristic();
    }
}