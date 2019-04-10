package com.ruxuanwo.template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取Jedis拦截功能
 *
 * @author ruxuanwo
 */
@Configuration
@ConfigurationProperties(prefix = "spring.jedisCluster")
public class JedisCluterConfiguration {

    private String nodes;
    private int timeout;

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisCluterConfiguration.class);

    /**
     * 为了防止redis未配置或配置错误，导致程序启动失败 在这里捕获异常并处理
     *
     * @return
     */
    @Bean
    public JedisCluster getJedisCluster() {
        if (nodes == null || "".equals(nodes)) {
            return null;
        }
        try {
            String[] serverArray = nodes.split(",");
            Set<HostAndPort> clusterNodes = new HashSet<>();
            for (String ipPort : serverArray) {
                String[] ipPortPair = ipPort.split(":");
                clusterNodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
            }
            return new JedisCluster(clusterNodes, timeout);
        } catch (Exception e) {
            LOGGER.error("JedisCluterConfiguration getJedisCluster Exception:", e);
        }
        return null;

    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
