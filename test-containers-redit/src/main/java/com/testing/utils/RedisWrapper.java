package com.testing.utils;

import redis.clients.jedis.Jedis;

public class RedisWrapper {

    private static Jedis jedis;

    public RedisWrapper(String hostname, Integer port) {
        jedis = new Jedis(hostname, port);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public void put(String key, String value) {
        jedis.set(key, value);
    }

}
