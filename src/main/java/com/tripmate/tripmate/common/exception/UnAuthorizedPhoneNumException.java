package com.tripmate.tripmate.common.exception;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.http.HttpStatus;

public class UnAuthorizedPhoneNumException extends CustomException {
    public UnAuthorizedPhoneNumException() {
        super(HttpStatus.UNAUTHORIZED, ResultCode.DUPLICATE_USERNAME);
    }
}
