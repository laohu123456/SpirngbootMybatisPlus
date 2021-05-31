package com.server.service.impl;


import com.server.common.Constant;
import com.server.service.OtherService;
import com.server.utils.RedisUtils;
import com.server.websocket.WebSocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OtherServiceImpl implements OtherService {

    private String redisSessionKey = RedisUtils.getRedisSessionKeyBak(RedisUtils.getRedisSessionKey());

    @Autowired
    private WebSocketUtils webSocketUtils;

    @Autowired
    @Qualifier("redisTemplateSer")
    private RedisTemplate redisTemplate;

    @Override
    public boolean saveTips(Integer expireTime) {
        redisTemplate.opsForValue().set(redisSessionKey, "");
        return redisTemplate.expire(redisSessionKey, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void sendMessage(Map<String,String> map) {
        Session session = webSocketUtils.get(map.get(Constant.SESSION_KEY_SESSIONID));
        String message = map.get(Constant.SESSION_KEY_MESSAGE);
        webSocketUtils.sendMessage(session, message);
    }
}
