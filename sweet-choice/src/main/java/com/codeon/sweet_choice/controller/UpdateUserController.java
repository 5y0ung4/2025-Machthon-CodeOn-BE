package com.codeon.sweet_choice.controller;

import com.codeon.sweet_choice.dto.UpdateUserDto;
import com.codeon.sweet_choice.service.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/mypage/info")
public class UpdateUserController {

    @Autowired
    private final UpdateUserService updateUserService;

    @GetMapping("/getUser")
    public ResponseEntity<UpdateUserDto> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Object principal = authentication.getPrincipal();
        String email;

        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = principal.toString();
        }

        UpdateUserDto userInfo = updateUserService.getUserInfo(email);
        return ResponseEntity.ok(userInfo);
    }


    @PatchMapping("/updateUser")
    public ResponseEntity<UpdateUserDto> updateMyProfile(
            @Valid @RequestBody UpdateUserDto request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Object principal = authentication.getPrincipal();
        String userEmail;

        if (principal instanceof UserDetails userDetails) {
            userEmail = userDetails.getUsername();
        } else {
            userEmail = principal.toString();
        }

        UpdateUserDto updatedInfo = updateUserService.updateUserInfo(userEmail, request);
        return ResponseEntity.ok(updatedInfo);
    }

}