package com.tripmate.tripmate.auth.service;

import com.tripmate.tripmate.auth.domain.Certification;
import com.tripmate.tripmate.auth.dto.SignUpDto;
import com.tripmate.tripmate.auth.repository.CertificationRepository;
import com.tripmate.tripmate.client.email.EmailProvider;
import com.tripmate.tripmate.common.exception.DuplicateEmailException;
import com.tripmate.tripmate.common.exception.DuplicateUsernameException;
import com.tripmate.tripmate.common.exception.MailSendFailException;
import com.tripmate.tripmate.common.exception.UnAuthorizedPhoneNumException;
import com.tripmate.tripmate.user.domain.AuthType;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final EmailProvider emailProvider;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createCertification(String email) {
        //중복된 번호로 가입시
        if(checkDuplicateEmail(email)) throw new DuplicateEmailException();

        //기존 번호의 인증코드가 있으면 삭제
        certificationRepository.deleteByPhoneNum(email);

        String certificationNum = createCertificationNumber();
        Certification certification = Certification.builder()
                .certificationNum(certificationNum)
                .email(email)
                .isCheck(false).build();

        boolean isEmailSendSuccessful = emailProvider.sendCertificationMail(email, certificationNum);
        if(!isEmailSendSuccessful) throw new MailSendFailException();

        certificationRepository.save(certification);
        System.out.println("저장 수행");
    }

    @Transactional
    public void certifyPhoneNum(String email, String certificationNum) {
        //중복된 번호로 가입시
        if(checkDuplicateEmail(email)) throw new DuplicateEmailException();

        Optional<Certification> findCertification = certificationRepository.findByEmailAndCertificationNum(email, certificationNum);

        Certification certification = findCertification.orElseThrow(IllegalArgumentException::new);
        certification.check();
    }

    @Transactional
    public void signUp(SignUpDto signUpDto, String certificationNum) {
        Optional<Certification> findCertification = certificationRepository.findByEmailAndCertificationNum(signUpDto.getEmail(), certificationNum);
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
                .email(signUpDto.getEmail()).build();
        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) { //중복된 username인 경우
            throw new DuplicateUsernameException();
        }
    }

    public boolean checkDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    private String createCertificationNumber() {
        String certificationNumber = "";
        //6자리 숫자로 구성된 번호
        for (int count = 0; count < 6; count++) certificationNumber += (int) (Math.random() * 10);
        return certificationNumber;
    }
}
