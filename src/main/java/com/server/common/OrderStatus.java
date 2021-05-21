package com.server.common;


import javax.validation.constraints.NotNull;

public enum OrderStatus {

    /**
     *   0 -> 提交状态   COMMIT
     *   1 -> 结算完成    COMPLETED
     *   2 -> 待结算   TOBESETTED
     *   3 -> 支付超时,作废订单作废  TOVOID
      */

    COMMIT(0,"提交订单,待结算"),
    COMPLETED(1,"结算完成"),
    //TOBESETTED(2,"待结算"),
    TOVOID(3,"支付超时,订单作废");

    private Integer code;
    private String message;

    OrderStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(@NotNull Integer code) {
        for(OrderStatus orderStatus: OrderStatus.values()){
            if(orderStatus.getCode() == code){
                return orderStatus.getMessage();
            }
        }
        return null;
    }



}
