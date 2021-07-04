package com.jaewoo.srs.common.message.dao

import com.jaewoo.srs.common.message.domain.entity.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageRepository : JpaRepository<Message, Long>, MessageDao {
    fun existsByKey(key: String): Boolean
    fun findByKey(key: String): Optional<Message>
}