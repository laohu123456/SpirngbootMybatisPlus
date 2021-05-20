package com.server.redis.listener;

import com.server.common.Constant;
import com.server.common.OrderStatus;
import com.server.entity.OrderInfo;
import com.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisListener implements MessageListener {

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        message(message, bytes);
    }

    public void message(Message message, byte[] bytes){
        updateOrderStatus(new String(message.getBody()));
    }

    public void updateOrderStatus(String redisKey){
        if(redisKey != null && !redisKey.isEmpty()){
            String orderId = redisKey.replace(Constant.REDIS_ORDER_LISTENER_KEY_PERFIX, "");
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(Long.valueOf(orderId));
            orderInfo.setOrderStatus(String.valueOf(OrderStatus.TOVOID.getCode()));
            orderService.update(orderInfo);
        }
    }

}
