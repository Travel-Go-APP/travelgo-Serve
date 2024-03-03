package com.travelgo.backend.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="location_id")
    private Location location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Picture(String imageUrl, Location location) {
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public Picture(String imageUrl, Item item) {
        this.imageUrl = imageUrl;
        this.item = item;
    }
}
