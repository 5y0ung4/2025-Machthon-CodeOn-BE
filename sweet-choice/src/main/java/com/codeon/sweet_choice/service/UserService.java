package com.codeon.sweet_choice.service;

import com.codeon.sweet_choice.config.JwtTokenProvider;
import com.codeon.sweet_choice.dto.LoginRequestDto;
import com.codeon.sweet_choice.dto.SignupRequestDto;
import com.codeon.sweet_choice.entity.User;
import com.codeon.sweet_choice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다. ");
        }
        if(userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다. ");
        }

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        User user = new User();
        user.setNickname(signupRequestDto.getNickname());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(encodedPassword);
        user.setFirstName(signupRequestDto.getFirstName());
        user.setLastName(signupRequestDto.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setState(signupRequestDto.getUserType());
        user.setHeight(signupRequestDto.getHeight());
        user.setWeight(signupRequestDto.getWeight());
        user.setSex(signupRequestDto.getSex());
        userRepository.save(user);

    }

    public String login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(loginRequestDto.getEmail());
        return token;

    }

}
