package com.tripmate.tripmate.auth.oauth.response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        System.out.println("키셋 "+ attribute.keySet());
        for (String key : attribute.keySet()) {
            Object object = attribute.get(key);
            System.out.println(key+" "+object.toString());
        }
        this.attribute = attribute;
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
