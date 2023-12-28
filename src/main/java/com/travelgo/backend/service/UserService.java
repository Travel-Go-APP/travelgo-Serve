package com.travelgo.backend.service;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.SignUpDTO;
import com.travelgo.backend.repository.UserRepository;
import com.vane.badwordfiltering.BadWordFiltering;
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
            return true; // 이미 등록된 회원일 때
        }
        return false; // 신규 회원일 때
    }


    public boolean hasDuplicateKakaoAccount(String kakaoId, String email) {
        if ((findUserByKakaoId(kakaoId) != null) && (findUserByEmail(email) != null)) {
            return true; // 이미 등록된 닉네임
        }
        return false; // 신규 닉네임
    }

    public boolean hasDuplicateNickname(String nickname) {
        if (findUserByNickname(nickname) != null) {
            return true; // 이미 등록된 닉네임
        }
        return false; // 신규 닉네임
    }

    public Boolean checkNickname(String nickname) {
        BadWordFiltering filtering = new BadWordFiltering();
        return filtering.check(nickname);
    }
}
