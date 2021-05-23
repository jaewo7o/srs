package com.jaewoo.srs.common.message.domain.dto

import com.jaewoo.srs.common.message.domain.entity.Message

class MessageDTO

data class CreateMessageRequest(
    val key: String,
    val contentsKo: String,
    val contentsEn: String
) {
    fun toEntitiy() = Message(
        key = key,
        contentsKo = contentsKo,
        contentsEn = contentsEn
    )
}

data class UpdateMessageRequest(
    var id: Long,
    val contentsKo: String,
    val contentsEn: String
)

data class SearchMessageRequest(
    var key: String?,
    var contents: String?
)