package com.hcx.asclepiusmanager.sysmgr.auth.config;

/**
 * @ClassName TokenIsExpiredException
 * @Description 自定义异常
 * @Author hcx
 * @Date 2022/2/9 15:29
 * @Version 1.0
 **/
public class TokenIsExpiredException extends Exception{
    public TokenIsExpiredException() {
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
