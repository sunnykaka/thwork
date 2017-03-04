package com.akkafun.thwork.exchange;

/**
 * Created by liubin on 2017/3/5.
 */
public class BadSentenceException extends RuntimeException {

    public BadSentenceException(String message) {
        super(message);
    }

    public BadSentenceException(String message, Throwable cause) {
        super(message, cause);
    }


}
