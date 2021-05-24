package com.server.redis.utils;

import com.server.annotation.LogMethodRecord;
import com.server.common.Constant;
import com.server.common.OrderStatus;
import com.server.dao.OrderInfoMapper;
import com.server.entity.OrderInfo;
import com.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JedisCommonUtils {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderService orderService;

    @Transactional
    @LogMethodRecord(value = "redis监听器,监听过期键值", uri = "")
    public void updateOrderStatus(String redisKey) {
        if(redisKey != null && !redisKey.isEmpty()){
            String orderId = redisKey.replace(Constant.REDIS_ORDER_LISTENER_KEY_PERFIX, "");
            OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
            if(orderInfo!= null && "0".equals(orderInfo.getOrderStatus())){
                orderInfo.setOrderId(Long.valueOf(orderId));
                orderInfo.setOrderStatus(String.valueOf(OrderStatus.TOVOID.getCode()));
                orderInfoMapper.updateById(orderInfo);
            }
        }
    }




    
}
