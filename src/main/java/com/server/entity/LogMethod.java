package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMethod implements Serializable {


    private static final long serialVersionUID = -4799966663089151414L;
    private Long id;

    private String methodName;

    private String methodAnnotationName;

    private Double second;

    private Long  msecond;

    private String createTime;

    private String uri;

    private String args;

    private String SystemName;

    private String remark;
}
