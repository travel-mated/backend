package com.tripmate.tripmate.auth.oauth.response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;
    private Map<String, Object> kakaoAccountAttributes;
    private Map<String, Object> profileAttributes;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccountAttributes = (Map<String, Object>) attribute.get("kakao_account");
        this.profileAttributes = (Map<String, Object>) attribute.get("profile");

    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String,Object> properties = (Map<String,Object>) attribute.get("properties");
        return properties.get("nickname").toString();
    }
}
