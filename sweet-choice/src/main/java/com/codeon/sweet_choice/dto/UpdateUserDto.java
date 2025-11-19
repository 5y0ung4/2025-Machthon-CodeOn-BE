package com.codeon.sweet_choice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String nickname;
    private String password;
    private Integer height;
    private Integer weight;
    private String userType;
    private Integer adi;

    public UpdateUserDto(String nickname, String password, Integer height, Integer weight, String userType, Integer adi) {
        this.nickname = nickname;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.userType = userType;
        this.adi = adi;
    }
}
