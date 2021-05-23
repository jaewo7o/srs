package com.jaewoo.srs.core.util

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class RedisUtil(
    private val stringRedisTemplate: StringRedisTemplate
) {

    fun getData(key: String): String? {
        val valueOperations = stringRedisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun setData(key: String, value: String) {
        val valueOperations = stringRedisTemplate.opsForValue()
        valueOperations[key] = value
    }

    fun setDataExpire(key: String, value: String, duration: Long) {
        val valueOperations = stringRedisTemplate.opsForValue()
        val expireDuration: Duration = Duration.ofSeconds(duration)
        valueOperations.set(key, value, expireDuration)
    }

    fun deleteData(key: String) = stringRedisTemplate.delete(key)
}