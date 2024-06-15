package com.tripmate.tripmate.common.exception;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.http.HttpStatus;

public class NotValidAccessTokenException extends CustomException {
    public NotValidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, ResultCode.NOT_VALID_ACCESS_TOKEN);
    }
}
