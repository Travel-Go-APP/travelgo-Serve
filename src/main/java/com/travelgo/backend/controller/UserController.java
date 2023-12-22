package com.travelgo.backend.controller;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.KakaoLoginRequestDTO;
import com.travelgo.backend.dto.LoginDTO;
import com.travelgo.backend.dto.SignUpDTO;
import com.travelgo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO){
        userService.signUp(signUpDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/kakao-login")
    public ResponseEntity<?> kakaoLogin(@RequestBody KakaoLoginRequestDTO kakaoLoginRequest) {
        if (userService.hasDuplicateKakaoId(kakaoLoginRequest.getKakaoId())) {
            // 등록돼 있지 않은 사용자 회원가입 처리
            SignUpDTO signUpDTO = new SignUpDTO();
            signUpDTO.setKakaoId(kakaoLoginRequest.getKakaoId());
            signUpDTO.setNickname(kakaoLoginRequest.getNickname());
            signUpDTO.setEmail(kakaoLoginRequest.getEmail());
            userService.signUp(signUpDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
            // kakaoAPI에서 불러온 정보를 kakaoLoginRequestDTO로 가져오고 그 데이터를 SignUpDTO로 보내 묶은 후 회원가입 처리
        } else {
            // 기존에 등록되어 있는 사용자의 로그인 처리
            User user = userService.findByKakaoId(kakaoLoginRequest.getKakaoId());
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setKakaoId(user.getKakaoId());
            return ResponseEntity.ok(loginDTO);
        }
    }
}
