package com.server.redis.utils;

import com.server.annotation.LogMethodRecord;
import com.server.common.Constant;
import com.server.common.OrderStatus;
import com.server.dao.OrderInfoMapper;
import com.server.entity.OrderInfo;
import com.server.service.OrderService;
import com.server.utils.HttpClientUtils;
import com.server.utils.RedisUtils;
import com.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JedisCommonUtils {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("redisTemplateSer")
    private RedisTemplate redisTemplate;

    @Transactional
    @LogMethodRecord(value = "redis监听器,监听过期键值,修改订单状态", uri = "")
    public void updateOrderStatus(String redisKey) {
        if(Utils.ifNull(redisKey)){
            String orderId = RedisUtils.splitSuffix(redisKey);
            OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
            if(orderInfo!= null && "0".equals(orderInfo.getOrderStatus())){
                orderInfo.setOrderId(Long.valueOf(orderId));
                orderInfo.setOrderStatus(String.valueOf(OrderStatus.TOVOID.getCode()));
                orderInfoMapper.updateById(orderInfo);
            }
        }
    }

    @LogMethodRecord(value = "redis监听器,监听过期键值,发送websocket消息", uri = "")
    public void sendWebSocketMessage(String redisKey){
        if(Utils.ifNull(redisKey)){
            String redisKeyBak = redisKey + Constant.REDIS_KEY_BAK;
            Set<String> range = redisTemplate.opsForZSet().range(redisKeyBak, 0, -1);
            System.out.println(range);
            if(!CollectionUtils.isEmpty(range)){
                for (String str : range) {
                    String[] split = Utils.split(str);
                    Map<String, String> map  = new HashMap<>();
                    map.put(Constant.SESSION_KEY_MESSAGE,"这里是一个webSocket推送的消息" + split[2]);
                    map.put(Constant.SESSION_KEY_SESSIONID, split[2]);
                    HttpClientUtils.post_init(split[0], Integer.valueOf(split[1]), "/SpirngbootMybatisPlus/other/sendMessage" ,map);
                }
            }
            redisTemplate.delete(redisKeyBak);
       }
    }





    
}
