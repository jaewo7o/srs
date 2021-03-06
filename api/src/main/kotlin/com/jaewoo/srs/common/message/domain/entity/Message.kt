package com.jaewoo.srs.common.message.domain.entity

import com.jaewoo.srs.common.message.domain.enum.MessageType
import com.jaewoo.srs.common.message.domain.enum.MessageTypeConverter
import com.jaewoo.srs.core.jpa.entity.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "message")
class Message(
    @Column(name = "message_key")
    var key: String,

    @Convert(converter = MessageTypeConverter::class)
    @Column(name = "message_type")
    var messageType: MessageType,

    @Column(name = "message_ko")
    var contentsKo: String,

    @Column(name = "message_en")
    var contentsEn: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    var id: Long? = null
}