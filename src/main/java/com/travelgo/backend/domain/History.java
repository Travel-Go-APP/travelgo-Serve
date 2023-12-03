package com.travelgo.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private LocalDate date;

    private String scedule;

    @Lob
    private String note;
}
