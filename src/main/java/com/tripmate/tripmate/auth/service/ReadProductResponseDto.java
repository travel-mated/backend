package com.tripmate.tripmate.auth.service;

import lombok.Builder;
import lombok.Data;

@Data
public class ReadProductResponseDto {
    long id;
    long userId;
    String name;
    long inventoryNum;
    long price;
    String description;
    String createdAt;
    String updatedAt;
// ZonedDateTime.parse function을 통해서 파싱이 가능한 형태여야 테스트를 통과할 수 있습니다.
// 예시: "2023-08-29T10:02:33.144718+09:00"

    @Builder

    private ReadProductResponseDto(long id, long userId, String name, long inventoryNum, long price, String description, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.inventoryNum = inventoryNum;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
