package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.dto.UpdateUserDto;
import com.codeon.sweet_choice.entity.User;
import com.codeon.sweet_choice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserDto getUserInfo(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보가 없습니다."));

        return new UpdateUserDto(
                user.getNickname(),
                user.getPassword(),
                user.getHeight(),
                user.getWeight(),
                user.getState(),
                user.getAdi()
        );
    }

    public UpdateUserDto updateUserInfo(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자가 없습니다."));


        if (updateUserDto.getNickname() != null) user.setNickname(updateUserDto.getNickname());
        if (updateUserDto.getHeight() != null) user.setHeight(updateUserDto.getHeight());
        if (updateUserDto.getWeight() != null) user.setWeight(updateUserDto.getWeight());
        if (updateUserDto.getUserType() != null) user.setState(updateUserDto.getUserType());
        if (updateUserDto.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(updateUserDto.getPassword());
            user.setPassword(encodedPassword);
        }

        int newAdi = userService.calculateAdiValue(user);
        user.setAdi(newAdi);

        userRepository.save(user);

        return new UpdateUserDto(
                user.getNickname(),
                user.getPassword(),
                user.getHeight(),
                user.getWeight(),
                user.getState(),
                newAdi
        );
    }

}

