package com.tripmate.tripmate.common;

import lombok.Getter;

@Getter
public class StatusResponse {
    private final String resultCode;
    private final String resultMessage;

    public StatusResponse(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
    }

    public StatusResponse(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
