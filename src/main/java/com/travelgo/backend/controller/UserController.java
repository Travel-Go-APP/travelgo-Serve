package com.travelgo.backend.controller;

import com.travelgo.backend.domain.User;
import com.travelgo.backend.dto.*;
import com.travelgo.backend.service.UserService;
import com.travelgo.exception.ErrorResponse;
import com.travelgo.exception.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @DeleteMapping("/{userId}")
    @Operation(summary = "유저 삭제")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        userService.delete(user);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/findUser")
    @Operation(summary = "Id로 유저 찾기")
    public ResponseEntity<?> getUserById(@RequestParam Long userId) {
        User user = userService.findUserById(userId);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findAll")
    @Operation(summary = "전체 유저 찾기")
    public ResponseEntity<?> getUserAll() {
        List<User> userList = userService.findAll();
        List<UserDTO> dtoList = userList.stream().map(UserDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/findUserByKakaoId")
    @Operation(summary = "Id로 유저 찾기")
    public ResponseEntity<?> getUserByKakaoId(@RequestParam String kakaoId) {
        User user = userService.findUserByKakaoId(kakaoId);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findUserByNickname")
    public ResponseEntity<?> getUserByNickname(@RequestParam String nickName) {
        User user = userService.findUserByNickname(nickName);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findUserByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
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
            loginDTO.setEmail(user.getEmail());
            loginDTO.setDetectionRange(user.getDetectionRange());
            loginDTO.setExperience(user.getExperience());
            loginDTO.setLevel(user.getLevel());
            loginDTO.setWorkCount(user.getWorkCount());
            loginDTO.setUserId(user.getUserId());
            return ResponseEntity.ok(loginDTO);
        } else {
            ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.ACCOUNT_DUPLICATION);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}