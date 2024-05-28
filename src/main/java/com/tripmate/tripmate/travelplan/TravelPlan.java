package com.tripmate.tripmate.travelplan;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class TravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DailyPlan> dailyPlans = new ArrayList<>();


    public void addDailyPlan(DailyPlan dailyPlan) {
        dailyPlans.add(dailyPlan);
        dailyPlan.setTravelPlan(this);
    }
}

