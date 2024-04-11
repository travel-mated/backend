package com.tripmate.tripmate.auth.controller;


import com.tripmate.tripmate.auth.dto.SignUpDto;
import com.tripmate.tripmate.auth.dto.request.CertificatePhoneNumRequestDto;
import com.tripmate.tripmate.auth.dto.request.CertifyPhoneNumRequestDto;
import com.tripmate.tripmate.auth.dto.request.SignUpRequestDto;
import com.tripmate.tripmate.auth.service.AuthService;
import com.tripmate.tripmate.common.ResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/certification/generate")
    public ResponseForm<String> createCertificate(@RequestBody CertificatePhoneNumRequestDto request) {
        authService.createCertification(request.getPhoneNum());
        return new ResponseForm<>();
    }

    @PostMapping("/certification/certify")
    public ResponseForm certify(@RequestBody CertifyPhoneNumRequestDto request) {
        authService.certifyPhoneNum(request.getPhoneNum(), request.getCertificationNum());
        return new ResponseForm();
    }

    @PostMapping("/sign-up")
    public ResponseForm signUp(@RequestBody SignUpRequestDto request) {
        SignUpDto signUpDto = requestToDto(request);
        authService.signUp(signUpDto, request.getCertificationNum());
        return new ResponseForm();
    }

    private static SignUpDto requestToDto(SignUpRequestDto requestDto) {
        return SignUpDto.builder()
                .username(requestDto.getUsername())
                .age(requestDto.getAge())
                .gender(requestDto.getGender())
                .mbti(requestDto.getMbti())
                .nickname(requestDto.getNickname())
                .phoneNumber(requestDto.getPhoneNumber())
                .password(requestDto.getPassword()).build();
    }
}
