package com.akkafun.thwork.roman;

/**
 * Created by liubin on 2017/3/4.
 */
public class RomanCalculateException extends RuntimeException {


    public RomanCalculateException(String message) {
        super(message);
    }

    public RomanCalculateException(String message, Throwable cause) {
        super(message, cause);
    }

}
