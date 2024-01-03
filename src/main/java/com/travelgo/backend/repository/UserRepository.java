package com.travelgo.backend.repository;

import com.travelgo.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByKakaoId(String kakaoId);

    User findByNickname(String nickname);

    User findByEmail(String email);

}
