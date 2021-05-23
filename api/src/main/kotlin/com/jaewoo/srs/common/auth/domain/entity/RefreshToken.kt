package com.jaewoo.srs.common.auth.domain.entity

import com.jaewoo.srs.app.user.domain.entity.User
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable
import javax.persistence.Id

@RedisHash("refreshToken")
data class RefreshToken(
    @Indexed val refreshToken: String,
    val user: User
) : Serializable {
    @get:Id
    var id: String? = null
}