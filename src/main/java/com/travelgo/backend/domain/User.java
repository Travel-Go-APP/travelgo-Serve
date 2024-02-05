package com.travelgo.backend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50, unique = true) //카카오 아이디 저장 부분
    private String kakaoId;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    //@Column(nullable = false,length = 100)
    //private String password;

    @Column(nullable = false, length = 50, unique = true)
    @Email
    private String email;

    private int experience;
    public void setExperience(int experience){
        this.experience = experience;
    }

    private int workCount;

    private int detectionRange;

    private int level;

}
