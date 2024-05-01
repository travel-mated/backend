package com.tripmate.tripmate.auth.repository;

import com.tripmate.tripmate.auth.domain.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Optional<Certification> findByEmailAndCertificationNum(String email, String certificationNum);

    void deleteByPhoneNum(String phoneNum);
}
