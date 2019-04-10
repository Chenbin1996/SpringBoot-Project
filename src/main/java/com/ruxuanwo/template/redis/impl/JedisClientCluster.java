package com.ruxuanwo.template.redis.impl;


import com.ruxuanwo.template.redis.INullTarget;
import com.ruxuanwo.template.redis.RedisClient;
import com.ruxuanwo.template.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * redis集群客户端
 *
 * @author ruxuanwo
 */
public class JedisClientCluster implements RedisClient {

    private JedisCluster jedisCluster;

    public static final Logger logger = LoggerFactory
            .getLogger(JedisClientCluster.class);

    /**
     * set成功返回的结果
     */
    private static final String SET_RESULT = "OK";

    /**
     * 凡是从数据库中查出的数据为空，则将该对象存入缓存中，避免缓存穿透,即多次向数据库查询
     */
    private static final String NULL_STRING = "15dbd89eeb1811e7bfeb000c29dc9bce";

    public JedisClientCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    @Override
    public <T> Boolean set(String key, T value) {
        return set(key, value, -1);
    }

    @Override
    public <T> Boolean set(String key, T value, int seconds) {
        String result = jedisCluster.set(key.getBytes(),
                SerializeUtil.serialize(value == null ? NULL_STRING : value));
        if (seconds > 0) {
            jedisCluster.expire(key, seconds);
        }
        return SET_RESULT.equalsIgnoreCase(result);
    }

    @Override
    public <T> Boolean set(String key, T value, String nxxx, String expx,
                           Long expire) {
        String result = jedisCluster.set(key.getBytes(),
                SerializeUtil.serialize(value == null ? NULL_STRING : value),
                nxxx.getBytes(), expx.getBytes(), expire);
        return SET_RESULT.equalsIgnoreCase(result);
    }

    @Override
    public <T> T get(String key) {
        byte[] result = jedisCluster.get(key.getBytes());
        return (result == null ? null : SerializeUtil.unSerialize(result));
    }

    @Override
    public String getString(String key) {
        return get(key);
    }

    @Override
    public <T> T get(String key, Class<T> t) {
        T obj = get(key);
        if (NULL_STRING.equals(obj)) {
            return null;
        }
        return obj;
    }

    @Override
    public <T> T get(String key, Class<T> t, INullTarget target, int second) {
        T obj = get(key);
        if (NULL_STRING.equals(obj)) {
            return null;
        }
        if (obj == null && target != null) {
            obj = (T) target.invoke(key);
            set(key, obj, second);
        }
        return obj;
    }

    @Override
    public <T> T get(String key, Class<T> t, INullTarget target) {
        return get(key, t, target, -1);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> t, INullTarget target) {
        return getList(key, t, target, -1);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> t, INullTarget target, int second) {
        Object result = get(key);
        // 说明数据库中也不存在
        if (result == NULL_STRING) {
            return Collections.emptyList();
        }
        List<T> obj = (List<T>) result;
        if (obj == null && target != null) {
            obj = (List<T>) target.invoke(key);
            set(key, obj, second);
        }
        return obj;
    }

    @Override
    public void batchDelByKyePrefixes(String kyePrefixes) {
        Set<String> set = this.getKeysByPrefixes(kyePrefixes + "*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String keyStr = it.next();
            jedisCluster.del(keyStr);
        }
    }

    @Override
    public void delByKey(String key) {
        jedisCluster.del(key);
    }

    @Override
    public Set<String> getKeys() {
        return this.getKeysByPrefixes("");
    }

    @Override
    public void expire(String key, int seconds) {
        jedisCluster.expire(key, seconds);
    }

    @Override
    public Boolean existsKey(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public Long pttl(String key) {
        return jedisCluster.pttl(key);
    }


    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public String hget(String key, String field) {
        byte[] result = jedisCluster.hget(key.getBytes(), field.getBytes());
        return (result == null ? null : SerializeUtil.unSerialize(result));
    }

    @Override
    public Long hset(String key, String field, String value) {
        return jedisCluster.hset(key.getBytes(), field.getBytes(),
                SerializeUtil.serialize(value == null ? NULL_STRING : value));
    }

    @Override
    public Long hdel(String key, String... fields) {
        byte[][] bytes = new byte[fields.length][];
        for (int i = 0; i < fields.length; i++) {
            bytes[i] = fields[i].getBytes();
        }
        return jedisCluster.hdel(key.getBytes(), bytes);
    }

    @Override
    public Boolean hexists(String key, String filed) {
        return jedisCluster.hexists(key.getBytes(), filed.getBytes());
    }

    @Override
    public <T> Map<String, T> hgetall(String key) {
        Map<String, T> result = new HashMap<>(16);
        Map<byte[], byte[]> maps = jedisCluster.hgetAll(key.getBytes());
        for (Map.Entry<byte[], byte[]> entry : maps.entrySet()) {
            result.put(new String(entry.getKey()), SerializeUtil.unSerialize(entry.getValue()));
        }
        return result;
    }

    @Override
    public Set<String> hkeys(String key) {
        return jedisCluster.hkeys(key);
    }

    @Override
    public Long hlen(String key) {
        return jedisCluster.hlen(key);
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return jedisCluster.hmget(key, fields);
    }

    @Override
    public Set<String> getKeysByPrefixes(String kyePrefixes) {
        TreeSet<String> keys = new TreeSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            JedisPool jp = entry.getValue();
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(kyePrefixes + "*"));
            } finally {
                closeJedis(connection);
            }
        }
        return keys;
    }

    @Override
    public void batchExpireByKyePrefixes(String kyePrefixes, int seconds) {
        Set<String> set = this.getKeysByPrefixes(kyePrefixes + "*");
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String keyStr = it.next();
            jedisCluster.exists(keyStr);
        }

    }

    @Override
    public void flushDB() {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            JedisPool jp = entry.getValue();
            Jedis connection = null;
            try {
                connection = jp.getResource();
                connection.flushDB();
            } finally {
                closeJedis(connection);
            }
        }
    }

    @Override
    public void flushAll() {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            JedisPool jp = entry.getValue();
            Jedis connection = null;
            try {
                connection = jp.getResource();
                jp.getResource();
                connection.flushAll();
            } finally {
                closeJedis(connection);
            }
        }

    }

    private void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
