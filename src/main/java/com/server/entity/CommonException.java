package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonException extends RuntimeException{

    private Integer code;
    private String message;

   public CommonException(String message){
       this.code = 500;
       this.message = message;
   }
}
