package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.config.CustomUserDetails;
import com.codeon.sweet_choice.dto.UpdateUserDto;
import com.codeon.sweet_choice.service.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/info")
public class UpdateUserController {

    @Autowired
    private final UpdateUserService updateUserService;

    @GetMapping("/getUser")
    public ResponseEntity<UpdateUserDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        UpdateUserDto userInfo = updateUserService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }


    @PatchMapping("/updateUser")
    public ResponseEntity<UpdateUserDto> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateUserDto request
    ) {
        Long userId = userDetails.getUserId();
        UpdateUserDto updatedInfo = updateUserService.updateUserInfo(userId, request);

        return ResponseEntity.ok(updatedInfo);
    }

}