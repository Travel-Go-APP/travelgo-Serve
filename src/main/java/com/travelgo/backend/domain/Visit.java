package com.travelgo.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.time.*;
import java.util.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long visitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;

    private LocalDateTime visitTime;

    public void setUser(User user){ this.user = user; }
    public void setLocation(Location location){ this.location = location; }

    public void setVisitTime(LocalDateTime visitTime){ this.visitTime = visitTime; }
    public static Visit createVisit(User user, Location location){
        Visit visit = new Visit();
        visit.setUser(user);
        visit.setLocation(location);
        visit.setVisitTime(LocalDateTime.now());
        return visit;
    }
}
