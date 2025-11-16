package com.codeon.sweet_choice.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter @Setter
public class SignupRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;

    private String firstName;
    private String lastName;

    // 사용자 상태
    private String userType;

    private Integer height;
    private Integer weight;

    private String sex;
}
