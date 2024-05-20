package com.tripmate.tripmate.common.exception;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.http.HttpStatus;

public class MailSendFailException extends CustomException {
    public MailSendFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ResultCode.MAIL_SEND_FAIL);
    }
}
