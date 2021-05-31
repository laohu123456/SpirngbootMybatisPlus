package com.server.service.impl;

import com.server.common.Constant;
import com.server.common.OrderStatus;
import com.server.dao.OrderInfoMapper;
import com.server.entity.CommonException;
import com.server.entity.OrderInfo;
import com.server.entity.ResponseMessage;
import com.server.service.OrderService;
import com.server.utils.RedisUtils;
import com.server.utils.SnowFlake;
import com.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    @Qualifier("redisTemplateSer")
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public ResponseMessage saveOrderInfo(OrderInfo orderInfo) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            long snowId = SnowFlake.nextId();
            orderInfo.setOrderId(snowId);
            orderInfo.setOrderCreatetime(Utils.getCurrentDate());
            orderInfo.setOrderStatus(String.valueOf(OrderStatus.COMMIT.getCode()));
            int insert = orderInfoMapper.insert(orderInfo);
            String redisKey = RedisUtils.getRedisKey(Constant.REDIS_ORDER_LISTENER_KEY_PREFIX, String.valueOf(snowId));
            redisTemplate.opsForValue().set(redisKey, "");
            redisTemplate.expire(redisKey, Constant.REDIS_ORDER_LISTENER_KEY_EXPIRE, TimeUnit.SECONDS);
            String message = insert > 0 ? "订单提交成功" : "订单提交失败";
            responseMessage.setMessage(message);
            responseMessage.setData(String.valueOf(snowId));
        }catch (Exception e){
            throw new CommonException("服务异常");
        }
        return responseMessage;
    }

    @Override
    public String findOrderById(String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        return OrderStatus.getMessage(Integer.valueOf(orderInfo.getOrderStatus()));
    }

    @Transactional
    @Override
    public void update(OrderInfo orderInfo) {
        orderInfoMapper.updateById(orderInfo);
    }
}
