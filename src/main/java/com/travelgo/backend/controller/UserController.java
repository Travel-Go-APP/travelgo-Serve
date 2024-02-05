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
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{id}")
    @Operation(summary = "유저 삭제")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        userService.delete(user);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/findUser/{id}")
    @Operation(summary = "Id로 유저 찾기")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/all")
    @Operation(summary = "전체 유저 찾기")
    public ResponseEntity<?> getUserAll() {
        List<User> userList = userService.findAll();
        List<UserDTO> dtoList = userList.stream().map(UserDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/findUserByKakaoId")
    @Operation(summary = "카카오 Id로 유저 찾기")
    public ResponseEntity<?> getUserByKakaoId(@RequestParam String kakaoId) {
        User user = userService.findUserByKakaoId(kakaoId);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findUserByNickname")
    @Operation(summary = "닉네임으로 유저 찾기")
    public ResponseEntity<?> getUserByNickname(@RequestParam String nickName) {
        User user = userService.findUserByNickname(nickName);
        UserDTO userDto = new UserDTO(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findUserByEmail")
    @Operation(summary = "이메일로 유저 찾기")
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

    @PostMapping("/check_nickname")
    @Operation(summary = "닉네임 체크")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        if (!userService.checkNickname(nickname)) {
            if (!userService.hasDuplicateNickname(nickname)) {
                // 사용 가능 닉네임
                return ResponseEntity.ok().body(null);
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

    @PostMapping("/kakao_check")
    @Operation(summary = "카카오 계정 체크")
    public ResponseEntity<?> kakaoCheck(@RequestParam String kakaoId) {
        if (userService.hasDuplicateKakaoId(kakaoId)) {
            return ResponseEntity.ok().body(null);
        } else {
            ErrorResponse errorResponse = ErrorResponse.from(GlobalErrorCode.ACCOUNT_NO_EXIST);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/updateExperience")
    @Operation(summary = "유저 경험치 업데이트")
    public ResponseEntity<?> updateExperience(@RequestBody ExperienceDTO experienceDTO) {
        long serverTime = System.currentTimeMillis();
        long timeDifference = serverTime - experienceDTO.getMillisecond(); //시간차 체크

        if (timeDifference < 0 || timeDifference > 5000) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid timestamp");
        } // 시간차가 5000ms(5초) 이내 확인

        User user = userService.findUserByKakaoId(experienceDTO.getKakaoId()); //ID로 유저 찾기

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        user.setExperience(user.getExperience() + experienceDTO.getExperience()); //경험치 증가

        userService.save(user); //DB update

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setLevel(user.getLevel());
        userResponseDTO.setExperience(user.getExperience());
        userResponseDTO.setWorkCount(user.getWorkCount()); //응답 DTO -- 형태 맞춰서 수정 예정

        return ResponseEntity.ok(userResponseDTO);
    }
}