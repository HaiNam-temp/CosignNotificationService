package com.cosign.NotificationService.exception;

import lombok.Getter;
import org.antlr.v4.runtime.atn.ErrorInfo;

@Getter
public class BusinessException extends RuntimeException {
    protected String errorCode;
    protected String errorMsg;

    public BusinessException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
