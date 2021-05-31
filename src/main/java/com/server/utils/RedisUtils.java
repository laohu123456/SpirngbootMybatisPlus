package com.server.utils;

import com.server.common.Constant;

public class RedisUtils {


    public static String getRedisKey(String key, String ywlsh){
        StringBuffer sbf = new StringBuffer();
        sbf.append(key);
        sbf.append(Constant.REDIS_SPLIT_KEY);
        sbf.append(ywlsh);
        return sbf.toString();
    }

    public static boolean matchPrefixRedisKey(String key ,String prefixRedis){
        if(key !=null && !key.isEmpty()){
            return key.split(Constant.REDIS_SPLIT_KEY, 2)[0].equals(prefixRedis);
        }
        return false;
    }

    public static String splitPrefix(String key){
        return key.split(Constant.REDIS_SPLIT_KEY, 2)[0];
    }

    public static String splitSuffix(String key){
        return key.split(Constant.REDIS_SPLIT_KEY, 2)[1];
    }

    public static String getRedisSessionKey(){
        return RedisUtils.getRedisKey(Constant.REDIS_SESSION_KEY_PREFIX, Constant.REDIS_SESSION_KEY_YWLSH);
    }

    public static String getRedisSessionKey(String ywlsh){
        return RedisUtils.getRedisKey(Constant.REDIS_SESSION_KEY_PREFIX, ywlsh);
    }

    public static String getRedisSessionKeyBak(String reidsKey){
        return reidsKey + Constant.REDIS_KEY_KEY;
    }


}
