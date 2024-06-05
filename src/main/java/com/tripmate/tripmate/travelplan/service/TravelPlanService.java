package com.tripmate.tripmate.travelplan.service;

import com.tripmate.tripmate.travelplan.TravelPlanRepository;
import com.tripmate.tripmate.travelplan.domain.TravelPlan;
import com.tripmate.tripmate.travelplan.dto.request.TravelPlanCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TravelPlanService {

    private TravelPlanRepository travelPlanRepository;

    //dailyPlan, event의 생성을 TravelPlan에게 위임해야 하나?
    //dailyPlan의 속성이 변한다면 TravelPlan에게 영향을 미치나?
    public void createTravelPlan(TravelPlanCreateRequest request) {

        TravelPlan travelPlan = new TravelPlan(user, name, dailyPlans);
        travelPlanRepository.save(travelPlan);
    }
}
