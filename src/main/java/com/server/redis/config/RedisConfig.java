package com.server.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.common.Constant;
import com.server.redis.listener.RedisListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private int redisTimeout;

    @Value("${spring.redis.password}")
    private String redisAuth;

    @Value("${spring.redis.database}")
    private int redisDb;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Bean(value = "RedisConnectionFactory")
    public RedisConnectionFactory getConnect() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMinIdle(minIdle);
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling().poolConfig(poolConfig).and().readTimeout(Duration.ofMillis(redisTimeout)).build();

        // 单点redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPassword(RedisPassword.of(redisAuth));
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDb);

        return new JedisConnectionFactory(redisConfig, clientConfig);
    }


    @Bean(value = "redisTemplateSer")
    public RedisTemplate<Object, Object> redisTemplateSer(@Qualifier("RedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplateSer = new RedisTemplate<>();
        redisTemplateSer.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplateSer.setValueSerializer(jsonRedisSerializer);
        redisTemplateSer.setKeySerializer(new StringRedisSerializer());
        redisTemplateSer.afterPropertiesSet();
        return redisTemplateSer;
    }

    @Bean
    public RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter,
                                                   @Qualifier("RedisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(Constant.REDIS_KEY_EVENT_EXPIRE));
        return container;
    }

    @Bean
    public MessageListenerAdapter adapter(RedisListener redisListener){
        return new MessageListenerAdapter(redisListener, "onMessage");
    }

}
