package com.hcsp.wxshop.entity;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    private int statusCode;
    private String messge;

    public static HttpException forbidden(String message) {
        return new HttpException(HttpStatus.FORBIDDEN.value(), message);
    }

    public static HttpException noutFount(String message) {
        return new HttpException(HttpStatus.NOT_FOUND.value(), message);
    }

    private HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }
}
