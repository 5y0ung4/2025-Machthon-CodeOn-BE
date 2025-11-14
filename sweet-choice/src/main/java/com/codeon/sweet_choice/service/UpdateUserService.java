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
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        return new UpdateUserDto(
                user.getNickname(),
                user.getPassword(),
                user.getHeight(),
                user.getWeight()
        );
    }

    public UpdateUserDto updateUserInfo(String email, UpdateUserDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));

        if (dto.getNickname() != null && !dto.getNickname().isBlank()) {
            user.setNickname(dto.getNickname());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getHeight() != null) {
            user.setHeight(dto.getHeight());
        }
        if (dto.getWeight() != null) {
            user.setWeight(dto.getWeight());
        }

        userRepository.save(user);

        return new UpdateUserDto(
                user.getNickname(),
                user.getPassword(),
                user.getHeight(),
                user.getWeight()
        );
    }

}

