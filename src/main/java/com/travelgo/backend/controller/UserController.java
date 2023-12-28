package com.travelgo.backend.controller;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.InitialAccountDTO;
import com.travelgo.backend.dto.KakaoLoginRequestDTO;
import com.travelgo.backend.dto.LoginDTO;
import com.travelgo.backend.dto.SignUpDTO;
import com.travelgo.backend.service.UserService;
import com.travelgo.exception.ErrorResponse;
import com.travelgo.exception.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        userService.signUp(signUpDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/initial")
    @Operation(summary = "닉네임 설정 전 유저")
    public ResponseEntity<?> initalUser(@RequestBody InitialAccountDTO initialAccountDTO) {
        if (!userService.hasDuplicateKakaoAccount(initialAccountDTO.getKakaoId(), initialAccountDTO.getEmail())) {
            // 사용 가능 계정
            return ResponseEntity.ok().body(initialAccountDTO);
        } else {
            // 이미 존재하는 계정
            ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.ACCOUNT_DUPLICATION);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/check-nickname")
    @Operation(summary = "닉네임 체크")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        if (!userService.checkNickname(nickname)) {
            if (!userService.hasDuplicateNickname(nickname)) {
                // 사용 가능 닉네임
                return ResponseEntity.ok().body(nickname);
            } else {
                // 이미 존재하는 닉네임
                ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.NICKNAME_DUPLICATION);
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } else {
            ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.NICKNAME_DISALLOWED);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/kakao-login")
    @Operation(summary = "카카오 로그인")
    public ResponseEntity<?> kakaoLogin(@RequestBody KakaoLoginRequestDTO kakaoLoginRequest) {
        if (userService.hasDuplicateKakaoId(kakaoLoginRequest.getKakaoId())) {
            User user = userService.findUserByKakaoId(kakaoLoginRequest.getKakaoId());
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setKakaoId(user.getKakaoId());
            return ResponseEntity.ok(loginDTO);
        } else {
            ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.ACCOUNT_DUPLICATION);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /*@PostMapping("/kakao-login")
    @Operation(summary = "카카오 로그인")
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
    }*/
}