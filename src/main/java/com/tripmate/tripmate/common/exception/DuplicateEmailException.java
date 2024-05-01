package com.tripmate.tripmate.common.exception;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super(HttpStatus.BAD_REQUEST, ResultCode.DUPLICATE_EMAIL);
    }
}
