package com.server.controller;

import com.server.annotation.LogMethodRecord;
import com.server.common.Constant;
import com.server.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/other")
public class OtherController {

    @Autowired
    private OtherService otherService;

    @LogMethodRecord(value = "mybatisplus与webSocket结合,初步测试,设置过期事件", uri = "/other/saveTips")
    @RequestMapping(value = "/saveTips")
    public String saveTips(@RequestParam("expire") Integer expire){
        return otherService.saveTips(expire) ? "设置成功" : "设置失败";
    }

    @LogMethodRecord(value = "mybatisplus与webSocket结合,初步测试,发布消息", uri = "/other/sendMessage")
    @RequestMapping(value = "/sendMessage")
    public void sendMessage(@RequestParam(Constant.SESSION_KEY_MESSAGE) String message,
                            @RequestParam(Constant.SESSION_KEY_SESSIONID) String sessionId){
        Map<String, String> map = new HashMap<>();
        map.put(Constant.SESSION_KEY_MESSAGE, message);
        map.put(Constant.SESSION_KEY_SESSIONID, sessionId);
        otherService.sendMessage(map);
    }

}
