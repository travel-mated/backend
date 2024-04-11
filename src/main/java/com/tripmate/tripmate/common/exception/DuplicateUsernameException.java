package com.tripmate.tripmate.common.exception;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends CustomException {
    public DuplicateUsernameException() {
        super(HttpStatus.BAD_REQUEST, ResultCode.DUPLICATE_PHONE_NUM);
    }
}
