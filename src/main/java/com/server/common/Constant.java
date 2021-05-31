package com.server.common;

public interface Constant {

    public final static String REDIS_KEY_EVENT_EXPIRE = "__keyevent@0__:expired";

    public final static String REDIS_ORDER_LISTENER_KEY_PREFIX = "redis:order:listener:key:prefix";

    public final static Integer REDIS_ORDER_LISTENER_KEY_EXPIRE = 30 * 1;

    public final static String  REDIS_SPLIT_KEY = "&";

    public final static String REDIS_SESSION_KEY_PREFIX = "redis:session:key:prefix";

    public final static String REDIS_SESSION_KEY_YWLSH = "606060606060123456";

    public final static String SESSION_KEY_MESSAGE = "message";

    public final static String SESSION_KEY_SESSIONID = "sessionId";

    public final static String REDIS_KEY_KEY = "_bak";

}
