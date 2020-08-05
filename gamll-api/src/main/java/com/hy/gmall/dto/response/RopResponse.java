package com.hy.gmall.dto.response;

import java.beans.Transient;
import java.io.Serializable;

public class RopResponse<T> implements Serializable {

    String code="1";
    String message="";
    String errorMessage="";
    String debugMsg;

    private T data;

    public RopResponse(T data, String code, String message, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.message = message;
        this.code = code;
    }

    public static <T> RopResponse success(T data, String message) {
        return new RopResponse<T>(data, "1", message, null);
    }

    public static <T> RopResponse error(String code, String errorMessage) {
        return new RopResponse<T>(null, code, null, errorMessage);
    }

    public static <T> RopResponse error(T data, String code, String errorMessage) {
        return new RopResponse<T>(data, code, null, errorMessage);
    }

    @Transient
    public boolean isSuccess() {
        return "1".equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(String debugMsg) {
        this.debugMsg = debugMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
