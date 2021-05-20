package com.server.entity;

import java.io.Serializable;

public class HttpResponseEntity implements Serializable {

    private static final long serialVersionUID = 2700497078220836579L;
    private Integer code;
    private String date;

    public HttpResponseEntity() {
    }

    public HttpResponseEntity(Integer code, String date) {
        this.code = code;
        this.date = date;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HttpResponseEntity{" +
                "code=" + code +
                ", date='" + date + '\'' +
                '}';
    }
}
