package com.travelgo.backend.service;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.SignUpDTO;
import com.travelgo.backend.repository.UserRepository;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
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

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 존재하지 않습니다."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
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

    public User save(User user){
        return userRepository.save(user);
    }
}
