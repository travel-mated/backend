package com.tripmate.tripmate.auth.service;

import com.tripmate.tripmate.auth.domain.Certification;
import com.tripmate.tripmate.auth.dto.SignUpDto;
import com.tripmate.tripmate.auth.repository.CertificationRepository;
import com.tripmate.tripmate.client.sms.SmsUtil;
import com.tripmate.tripmate.common.exception.DuplicatePhoneNumException;
import com.tripmate.tripmate.common.exception.DuplicateUsernameException;
import com.tripmate.tripmate.common.exception.UnAuthorizedPhoneNumException;
import com.tripmate.tripmate.user.domain.AuthType;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final SmsUtil smsUtil;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createCertification(String phoneNum) {
        //중복된 번호로 가입시
        if(checkDuplicatePhoneNum(phoneNum)) throw new DuplicatePhoneNumException();

        //기존 번호의 인증코드가 있으면 삭제
        certificationRepository.deleteByPhoneNum(phoneNum);

        String certificationNum = createCertificationNumber();
        Certification certification = Certification.builder()
                .certificationNum(certificationNum)
                .phoneNum(phoneNum)
                .isCheck(false).build();

        SingleMessageSentResponse singleMessageSentResponse = smsUtil.sendOne(phoneNum, certificationNum);
        System.out.println(singleMessageSentResponse);

        certificationRepository.save(certification);
        System.out.println("저장 수행");
    }

    @Transactional
    public void certifyPhoneNum(String phoneNum, String certificationNum) {
        //중복된 번호로 가입시
        if(checkDuplicatePhoneNum(phoneNum)) throw new DuplicatePhoneNumException();

        Optional<Certification> findCertification = certificationRepository.findByPhoneNumAndCertificationNum(phoneNum, certificationNum);

        Certification certification = findCertification.orElseThrow(IllegalArgumentException::new);
        certification.check();
    }

    @Transactional
    public void signUp(SignUpDto signUpDto, String certificationNum) {
        Optional<Certification> findCertification = certificationRepository.findByPhoneNumAndCertificationNum(signUpDto.getPhoneNumber(), certificationNum);
        Certification certification = findCertification.orElseThrow(IllegalArgumentException::new);
        if(!certification.getIsCheck()) throw new UnAuthorizedPhoneNumException();

        User newUser = User.builder().role("ROLE_USER")
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .password(bCryptPasswordEncoder.encode(signUpDto.getPassword()))
                .authType(AuthType.PHONE_MAIL)
                .age(signUpDto.getAge())
                .gender(signUpDto.getGender())
                .mbti(signUpDto.getMbti())
                .phoneNumber(signUpDto.getPhoneNumber()).build();
        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) { //중복된 username인 경우
            throw new DuplicateUsernameException();
        }
    }

    public boolean checkDuplicatePhoneNum(String phoneNum) {
        return userRepository.existsByPhoneNumber(phoneNum);
    }


    private String createCertificationNumber() {
        String certificationNumber = "";
        //6자리 숫자로 구성된 번호
        for (int count = 0; count < 6; count++) certificationNumber += (int) (Math.random() * 10);
        return certificationNumber;
    }
}
