package com.travelgo.backend.domain;

import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDrop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drop_id")
    private Long dropId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime dropTime;
}
