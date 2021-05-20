package com.server.controller;

import com.server.entity.OrderInfo;
import com.server.entity.ResponseMessage;
import com.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "saveOrder")
    public ResponseMessage saveOrder(@RequestBody OrderInfo orderInfo){
        return orderService.saveOrderInfo(orderInfo);
    }

    @RequestMapping(value = "findOrderById")
    public String findOrderById(@RequestParam("orderId") String orderId){
        return orderService.findOrderById(orderId);
    }


}
