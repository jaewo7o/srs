package com.jaewoo.srs.common.auth.domain.entity

import com.jaewoo.srs.common.auth.domain.vo.SessionUser
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import javax.persistence.Id

@RedisHash(
    value = "cacheSession",
    timeToLive = 3600L
)
data class CacheSession(
    val sessionUser: SessionUser
) : Serializable {
    @get:Id
    var id: String? = null
}