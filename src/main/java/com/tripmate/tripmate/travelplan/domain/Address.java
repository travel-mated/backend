package com.tripmate.tripmate.travelplan.domain;

import jakarta.persistence.*;

@Embeddable
public class Address {

    private String name;

    //위도
    private String latitude;
    //경도
    private String longitude;

}
