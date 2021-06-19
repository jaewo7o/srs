package com.jaewoo.srs.core.jpa.entity

import com.jaewoo.srs.core.context.SrsContext
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_user_id", nullable = false, updatable = false)
    var createdUserId: Long? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_user_id", nullable = false)
    var updatedUserId: Long? = null

    @Column(name = "updated_at", nullable = false)
    lateinit var updatedAt: LocalDateTime

    @PrePersist
    fun setPrePersist() {
        val userId = SrsContext.getCurrentUser()?.id ?: 0L

        createdUserId = userId
        createdAt = LocalDateTime.now()

        updatedUserId = userId
        updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun setPreUpdate() {
        val userId = SrsContext.getCurrentUser()?.id ?: 0L

        updatedUserId = userId
        updatedAt = LocalDateTime.now()
    }
}