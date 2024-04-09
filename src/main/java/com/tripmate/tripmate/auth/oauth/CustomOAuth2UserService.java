package com.tripmate.tripmate.auth.oauth;

import com.tripmate.tripmate.auth.oauth.response.KakaoResponse;
import com.tripmate.tripmate.auth.oauth.response.OAuth2Response;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.dto.UserDto;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2유저 : "+oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        Optional<User> existData = userRepository.findByUsername(username);

        if(!existData.isPresent()){
            User newUser = User.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .role("ROLE_USER")
                    .build();
            userRepository.save(newUser);
        }

        UserDto userDto = UserDto.builder()
                .name(oAuth2Response.getName())
                .username(username)
                .role("ROLE_USER")
                .build();

        return new CustomOAuth2User(userDto);
    }
}
