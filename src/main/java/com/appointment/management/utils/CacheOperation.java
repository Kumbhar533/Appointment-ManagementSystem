package com.appointment.management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.management.configuration.RedisConfig;

@Service("cacheOperation")
public class CacheOperation {

	@Autowired
	private RedisConfig cc;

	public CacheOperation() {
		super();
	}

	public Boolean isKeyExist(String key1, String key2) {
		return cc.redisTemplate().opsForHash().hasKey(key1, key2);
	}

	public void addInCache(String key1, String key2, Object val) {
		cc.redisTemplate().opsForHash().put(key1, key2, val);
	}

	public Object getFromCache(String key1, String key2) {
		return cc.redisTemplate().opsForHash().get(key1, key2);
	}

	public void removeKeyFromCache(String key) {
		cc.redisTemplate().delete(key);
		return;
	}

	public void removeAllKeysStartingWith() {
		cc.lettuceConnectionFactory().getConnection().flushAll();

	}

}
