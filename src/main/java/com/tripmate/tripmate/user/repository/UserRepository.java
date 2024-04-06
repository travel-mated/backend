package com.tripmate.tripmate.user.repository;


import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface UserRepository extends JpaRepository<User, Long> {

    default User getById(Long userId) {
        return findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
    }
}
