package com.server.entity;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("a_order_info")
public class OrderInfo implements Serializable {


    private static final long serialVersionUID = -7042794295032449111L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;

    @TableField(value = "order_name")
    private String orderName;

    //@TableField(fill = FieldFill.INSERT)
    private String orderCreatetime;
    private String orderStatus;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderCreatetime() {
        return orderCreatetime;
    }

    public void setOrderCreatetime(String orderCreatetime) {
        this.orderCreatetime = orderCreatetime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderCreatetime='" + orderCreatetime + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
