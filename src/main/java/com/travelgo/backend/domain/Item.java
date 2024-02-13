package com.travelgo.backend.domain;

import lombok.*;
import java.util.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @OneToOne(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Picture picture;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private String itemImage;

    private String itemName;

    private String itemDescription;
}
