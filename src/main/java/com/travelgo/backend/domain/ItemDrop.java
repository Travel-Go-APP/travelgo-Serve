package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;

    private LocalDateTime dropTime;

    public void changeDropTime(){
        this.dropTime = LocalDateTime.now();
    }
}
