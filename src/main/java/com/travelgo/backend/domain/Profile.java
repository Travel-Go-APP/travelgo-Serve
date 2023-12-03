package com.travelgo.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Profile_id")
    private Long profileId;

    @OneToOne(mappedBy = "profile")
    private User user;

    @OneToMany(mappedBy = "profile")
    private List<History> historys = new ArrayList<>();

}
