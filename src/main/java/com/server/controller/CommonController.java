package com.server.controller;

import com.server.entity.CommonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:37
 */
@ControllerAdvice
public class CommonController {

    @ExceptionHandler(value = {CommonException.class})
    @ResponseBody
    public Map<String, Object> parseException(CommonException commonException){
        Map<String,Object> map = new HashMap<>();
        map.put("code", commonException.getCode());
        map.put("message", commonException.getMessage());
        return map;
    }


}
