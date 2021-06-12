package com.jaewoo.srs.core.jpa.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at", nullable = false)
    lateinit var updatedAt: LocalDateTime

    @PrePersist
    fun setPrePersist() {
        createdAt = LocalDateTime.now()
        updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun setPreUpdate() {
        updatedAt = LocalDateTime.now()
    }
}