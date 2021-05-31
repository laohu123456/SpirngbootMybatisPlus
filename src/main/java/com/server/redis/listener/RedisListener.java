package com.server.redis.listener;

import com.server.common.Constant;
import com.server.redis.utils.JedisCommonUtils;
import com.server.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisListener implements MessageListener {

    @Autowired
    private JedisCommonUtils jedisCommonUtils;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        message(message, bytes);
    }

    public void message(Message message, byte[] bytes){
        String channelName = new String(bytes);
        if(Constant.REDIS_KEY_EVENT_EXPIRE.equals(channelName)){
            String redisExpireKey = new String(message.getBody());
            match(redisExpireKey);
        }
    }

    public void match(String redisKey){
        String splitStr = RedisUtils.splitPrefix(redisKey);
        switch (splitStr){
            case Constant.REDIS_ORDER_LISTENER_KEY_PREFIX:
                jedisCommonUtils.updateOrderStatus(redisKey);
                break;

            case Constant.REDIS_SESSION_KEY_PREFIX:
                jedisCommonUtils.sendWebSocketMessage(redisKey);
                break;

            default:
                System.out.println("NOT MATCH");
                break;
        }
    }


    /**
     * 在编写此功能时遇到@Transactional不生效问题
     *
     * 原因时 如何
     *    @Transactional
     *     @LogMethodRecord(value = "redis监听器,监听过期键值", uri = "")
     *     public void updateOrderStatus(String redisKey) {}
     *
     *     方法这样标注 在被其他方法调用时，没有aop增强 相当于this.updateOrderStatus
     *     所以无效
     *
     *
     *    事务注解不起作用原因：
     *      1.数据库底层不支持
     *      2.方法不是public
     *      3.异常被捕获了
     *      4.方法被this了
     *      5.rollbackFor 回滚异常设置有问题
     *      6.noRollbackFor 不回滚异常设置有问题
     *      7.propagation 事务属性设置不正确
     *          (1) Propagation.SUPPORTS
     *          (2) Propagation.NOT_SUPPORTED
     *          (3) Propagation.NEVER
     *      8.重复扫描包 context:component-scan
     *      加载顺序
     *      Springmvc配置文件的时候把service也加载了，但是此时事务还没加载，将会导致事务无法成功生效
     *
     */



}
