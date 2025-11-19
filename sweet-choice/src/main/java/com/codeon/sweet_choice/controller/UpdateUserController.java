package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.UpdateUserDto;
import com.codeon.sweet_choice.service.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/mypage/info")
public class UpdateUserController {

    @Autowired
    private final UpdateUserService updateUserService;

    @GetMapping("/{userId}")
    public ResponseEntity<UpdateUserDto> getUserInfo(@PathVariable Long userId) {
        UpdateUserDto userInfo = updateUserService.getUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserDto> updateMyProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserDto request
    ) {
        UpdateUserDto updatedInfo = updateUserService.updateUserInfo(userId, request);

        return ResponseEntity.ok(updatedInfo);
    }

}