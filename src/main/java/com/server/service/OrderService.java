package com.server.service;

import com.server.entity.OrderInfo;
import com.server.entity.ResponseMessage;

public interface OrderService {

    public ResponseMessage saveOrderInfo(OrderInfo orderInfo);

    public String findOrderById(String orderId);

    public void update(OrderInfo orderInfo);


}
