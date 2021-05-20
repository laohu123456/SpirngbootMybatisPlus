package com.server.common;

public interface Constant {

    public final static String REDIS_KEY_EVENT_EXPIRE = "__keyevent@0__:expired";

    public final static String REDIS_ORDER_LISTENER_KEY_PERFIX = "redis:order:listener:key:perfix:";

    public final static Integer REDIS_ORDER_LISTENER_KEY_EXPIRE = 30 * 1;
}
