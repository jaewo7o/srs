package com.jaewoo.srs.common.message.domain.entity

import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "message")
class Message(
    @Column(name = "message_key")
    var key: String,

    @Column(name = "message_ko")
    var contentsKo: String,

    @Column(name = "message_en")
    var contentsEn: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}