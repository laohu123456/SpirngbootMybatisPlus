package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:36
 */
@Data
@AllArgsConstructor
public class CommonException extends RuntimeException{

    private Integer code;
    private String message;


}
