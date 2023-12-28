package com.travelgo.backend.service;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.KakaoLoginRequestDTO;
import com.travelgo.backend.dto.SignUpDTO;
import com.travelgo.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void signUp(SignUpDTO signUpDTO) {
        User user = User.builder()
                .kakaoId(signUpDTO.getKakaoId())
                //.password(signUpDTO.getPassword())
                .email(signUpDTO.getEmail())
                .nickname(signUpDTO.getNickname())
                .experience(0)
                .workCount(0)
                .detectionRange(1)
                .level(1)
                .build();

        userRepository.save(user);
    }

    public boolean hasDuplicateKakaoId(String kakaoId) {
        if (userRepository.findByKakaoId(kakaoId) != null) {
            new IllegalStateException("이미 존재하는 카카오 아이디입니다.");
            return true; // 이미 등록된 회원일 때
        }
        return false; // 신규 회원일 때
    }

    public boolean hasDuplicateKakaoAccount(String kakaoId, String email) {
        if ((userRepository.findByKakaoId(kakaoId) != null) && (userRepository.findByEmail(email) != null)) {
            return true; // 이미 등록된 닉네임
        }
        return false; // 신규 닉네임
    }

    public boolean hasDuplicateNickname(String nickname) {
        if (userRepository.findByNickname(nickname) != null) {
            return true; // 이미 등록된 닉네임
        }
        return false; // 신규 닉네임
    }
}
