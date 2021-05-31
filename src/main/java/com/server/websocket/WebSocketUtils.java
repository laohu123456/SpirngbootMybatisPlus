package com.server.websocket;

import com.server.common.Constant;
import com.server.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("webSocketUtils")
public class WebSocketUtils {


    @Autowired
    @Qualifier("redisTemplateSer")
    private RedisTemplate redisTemplate;

    @Value("${websocket.server-ip}")
    private String host;

    @Value("${websocket.server-port}")
    private Integer port;
    /**
     *  分布式Session解决方案
     *
     *  REDIS_WEBSOCKT_SESSOION_SHARE:业务ID 设计过期时间  Redis的ZSET数据结构
     *  Redis监听器监听过期事件  获取业务ID和 webSocket的Session信息 利用HttpClient进行消息分发 IP:PROT
     *
     *  IP:PROT:USERID:SESSSIONID
     */


    public static Map<String, Session> map = new ConcurrentHashMap<>();

    private String redisSessionKey = RedisUtils.getRedisSessionKeyBak(RedisUtils.getRedisSessionKey());

    public void add(String sessionId, Session session){
        map.put(sessionId, session);
        redisTemplate.opsForZSet().add(redisSessionKey + Constant.REDIS_KEY_KEY, combinationRedisSessionValue(sessionId), createDoubleNumber());
    }


    private Double createDoubleNumber(){
        return Math.random();
    }


    public void remove(String sessionId){
        map.remove(sessionId);
        redisTemplate.opsForZSet().remove(redisSessionKey + Constant.REDIS_KEY_KEY, combinationRedisSessionValue(sessionId));
    }

    public Session get(String sessionId){
        return map.get(sessionId);
    }

    public void sendMessage(Session session, String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String combinationRedisSessionValue(String sessionId){
        //IP:PORT:sessionId
        StringBuffer sbf = new StringBuffer();
        sbf.append(host);
        sbf.append(Constant.REDIS_SPLIT_KEY);
        sbf.append(String.valueOf(port));
        sbf.append(Constant.REDIS_SPLIT_KEY);
        sbf.append(sessionId);
        return sbf.toString();
    }


}
