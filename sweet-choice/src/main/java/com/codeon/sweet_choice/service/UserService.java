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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
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

        //adi계산
        Integer adi = calculateAdiValue(user);
        user.setAdi(adi);

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


    public int calculateAdiValue(User user) {
        // 1. 필수 정보 없으면 0 리턴 (방어 코드)
        // 실제 몸무게를 사용하므로 weight가 0이면 계산 불가
        if (user.getWeight() == null || user.getWeight() == 0) {
            return 0;
        }

        // 2. 실제 몸무게 가져오기 (kg)
        double currentWeight = user.getWeight();

        // 3. 하루 권장 칼로리 계산 (실제 몸무게 * 활동지수 30)
        // 공식 변경: 표준 체중(X) -> 실제 몸무게(O)
        double dailyCalories = currentWeight * 30;

        // 4. 상태(State)에 따른 당 섭취 비율 설정
        // A: 일반/다이어트 (10%), B/C: 혈당관리 (5%)
        double sugarRatio;
        String state = user.getState();

        if ("B".equals(state) || "C".equals(state)) {
            sugarRatio = 0.05; // 5% (엄격)
        } else {
            sugarRatio = 0.1;  // 10% (일반 - WHO 권고)
        }

        // 5. ADI 계산 (g 단위 변환)
        // (총 칼로리 * 비율) / 4kcal
        return (int) ((dailyCalories * sugarRatio) / 4);
    }


}
