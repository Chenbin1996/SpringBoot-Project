package com.ruxuanwo.template.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis客户端
 *
 * @author ruxuanwo
 */
public interface RedisClient {

    /**
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    <T> Boolean set(String key, T value);

    /**
     * 该命令会移除原来的key的expire time
     *
     * @param key
     * @param value
     * @param seconds 时间，以秒为单位
     * @param <T>
     * @return
     */
    <T> Boolean set(String key, T value, int seconds);

    /**
     * 存储数据到缓存中，并制定过期时间和当Key存在时是否覆盖。
     *
     * @param key
     * @param value
     * @param nxxx   nxxx的值只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，
     *               则只有当key已经存在时才进行set
     * @param expx   expx的值只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
     * @param expire 过期时间，单位是expx所代表的单位。
     * @return
     */
    <T> Boolean set(String key, T value, String nxxx, String expx,
                    final Long expire);

    /**
     * 根据key获取缓存中的信息
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);

    /**
     * 根据key获取缓存中string类型的信息
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 根据key获取给定类型的结果
     *
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> t);

    /**
     * 获取数据
     *
     * @param key
     * @param t
     * @param target
     * @param second 过期时间
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> t, INullTarget target, int second);

    /**
     * @param key
     * @param t
     * @param target
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> t, INullTarget target);

    /**
     * @param key
     * @param t
     * @param target
     * @param <T>
     * @return
     */
    <T> List<T> getList(String key, Class<T> t, INullTarget target);

    /**
     * @param key
     * @param t
     * @param target
     * @param second 过期时间
     * @param <T>
     * @return
     */
    <T> List<T> getList(String key, Class<T> t, INullTarget target, int second);

    /**
     * redis批量删除以某字符串前缀的key
     *
     * @param kyePrefixes
     */
    void batchDelByKyePrefixes(String kyePrefixes);

    /**
     * redis删除key
     *
     * @param key
     */
    void delByKey(String key);

    /**
     * 返回所有的keys
     *
     * @return
     */
    Set<String> getKeys();

    /**
     * 返回所有以某字符串前缀的的keys
     *
     * @return
     */
    Set<String> getKeysByPrefixes(String kyePrefixes);

    /**
     * 设置key的过期时间，单位：秒
     *
     * @param key
     * @param seconds
     * @return
     */
    void expire(String key, int seconds);

    /**
     * redis批量设置以某字符串前缀的key的过期时间
     *
     * @param kyePrefixes
     */
    void batchExpireByKyePrefixes(String kyePrefixes, int seconds);

    /**
     * 检查key是否存在
     *
     * @param key
     * @return
     */
    Boolean existsKey(String key);

    /**
     * 返回key剩余的过期时间 - 1:永不过期 -2：key不存在，否则，以毫秒为单位，返回 key 的剩余生存时间
     *
     * @param key
     * @return -1:永不过期 -2：key:不存在
     */
    Long pttl(String key);

    /**
     * 将 key 中储存的数字值增一
     *
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 返回给定 key 的剩余生存时间
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 返回哈希表 key 中给定域 field 的值
     *
     * @param key
     * @param field
     * @return
     */
    String hget(String key, String field);

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hset(String key, String field, String value);

    /**
     * 删除哈希表 key 中的一个或多个指定域
     *
     * @param key
     * @param fields
     * @return
     */
    Long hdel(String key, String... fields);

    /**
     * 查看哈希表 key 中，给定域 field 是否存在
     *
     * @param key
     * @param filed
     * @return
     */
    Boolean hexists(String key, String filed);

    /**
     * 返回哈希表 key 中，所有的域和值
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> Map<String, T> hgetall(String key);

    /**
     * 返回哈希表 key 中的所有域
     *
     * @param key
     * @return
     */
    Set<String> hkeys(String key);

    /**
     * 返回哈希表 key 中域的数量
     *
     * @param key
     * @return
     */
    Long hlen(String key);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值
     *
     * @param key
     * @param fields
     * @return
     */
    List<String> hmget(String key, String... fields);

    /**
     * 清空当前数据库中的所有 key
     *
     * @return
     */
    void flushDB();

    /**
     * 清空整个 Redis 服务器的数据(删除所有数据库的所有 key )
     *
     * @return
     */
    void flushAll();
}
