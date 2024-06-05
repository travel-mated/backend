package com.tripmate.tripmate.travelplan.cotroller;

import com.tripmate.tripmate.travelplan.dto.request.TravelPlanCreateRequest;
import com.tripmate.tripmate.travelplan.service.TravelPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/travelplan")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @PostMapping()
    public void createTravelPlan(@RequestBody TravelPlanCreateRequest request) {
        travelPlanService.createTravelPlan(request);
    }
}
