package com.jaewoo.srs.common.auth.domain.entity

import com.jaewoo.srs.common.auth.domain.vo.Session
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable
import javax.persistence.Id

@RedisHash("cacheSession")
data class CacheSession(
    val session: Session
) : Serializable {
    @get:Id
    var id: String? = null
}