package com.ruxuanwo.template.redis.impl;

import com.ruxuanwo.template.redis.INullTarget;
import com.ruxuanwo.template.redis.RedisClient;
import com.ruxuanwo.template.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * redis单机客户端
 *
 * @author ruxuanwo
 */
public class JedisClientSingle implements RedisClient {

    private JedisPool jedisPool;

    public JedisClientSingle(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * set成功返回的结果
     */
    private static final String SET_RESULT = "OK";

    /**
     * 凡是从数据库中查出的数据为空，则将该对象存入缓存中，避免缓存穿透,即多次向数据库查询
     */
    private static final String NULL_STRING = "15dbd89eeb1811e7bfeb000c29dc9bce";

    @Override
    public <T> Boolean set(String key, T value) {
        return set(key, value, -1);
    }

    @Override
    public <T> Boolean set(String key, T value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.set(key.getBytes(),
                    SerializeUtil.serialize(value == null ? NULL_STRING : value));
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return SET_RESULT.equalsIgnoreCase(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <T> Boolean set(String key, T value, String nxxx, String expx,
                           Long expire) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.set(key.getBytes(),
                    SerializeUtil.serialize(value == null ? NULL_STRING : value),
                    nxxx.getBytes(), expx.getBytes(), expire);
            return SET_RESULT.equalsIgnoreCase(result);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <T> T get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] result = jedis.get(key.getBytes());
            return (result == null ? null : SerializeUtil.unSerialize(result));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String getString(String key) {
        return get(key, String.class);
    }

    @Override
    public <T> T get(String key, Class<T> t) {
        return get(key, t, null);
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
    public <T> T get(String key, Class<T> t, INullTarget target, int second) {
        // 说明数据库中也不存在
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
    public void batchDelByKyePrefixes(String kyePrefixes) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> set = jedis.keys(kyePrefixes + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedis.del(keyStr);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void delByKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Set<String> getKeys() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys("*");
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Set<String> getKeysByPrefixes(String kyePrefixes) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(kyePrefixes + "*");
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, seconds);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Boolean existsKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long pttl(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.pttl(key);
        } finally {
            closeJedis(jedis);

        }
    }


    @Override
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key, field, (value == null ? NULL_STRING
                    : value));
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key, fields);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Boolean hexists(String key, String filed) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key, filed);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public <T> Map<String, T> hgetall(String key) {
        Jedis jedis = null;
        Map<String, T> result = new HashMap<>(16);
        try {
            jedis = jedisPool.getResource();
            Map<byte[], byte[]> maps = jedis.hgetAll(key.getBytes());
            for (Map.Entry<byte[], byte[]> entry : maps.entrySet()) {
                result.put(new String(entry.getKey()), SerializeUtil.unSerialize(entry.getValue()));
            }
            return result;
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hkeys(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public Long hlen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hlen(key);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key, fields);
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void batchExpireByKyePrefixes(String kyePrefixes, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> set = jedis.keys(kyePrefixes + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedis.expire(keyStr, seconds);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    @Override
    public void flushDB() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushDB();
        } finally {
            closeJedis(jedis);
        }

    }

    @Override
    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();
        } finally {
            closeJedis(jedis);
        }

    }

    private void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
