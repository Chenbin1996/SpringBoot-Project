package com.ruxuanwo.template.config;


import com.ruxuanwo.template.redis.RedisClient;
import com.ruxuanwo.template.redis.impl.JedisClientCluster;
import com.ruxuanwo.template.redis.impl.JedisClientSingle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端，单机还是集群
 *
 * @author ruxuanwo
 */
@Configuration
public class RedisClientConfiguration {
    @Bean
    public RedisClient getJedisClient(JedisPool jedisPool, JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            return new JedisClientCluster(jedisCluster);
        }
        if (jedisPool != null) {
            return new JedisClientSingle(jedisPool);
        }
        return null;
    }
}
