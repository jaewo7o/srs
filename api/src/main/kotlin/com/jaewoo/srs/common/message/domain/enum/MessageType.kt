package com.jaewoo.srs.common.message.domain.enum

import com.jaewoo.srs.core.enum.BaseEnum

enum class MessageType(
    private val code: String,
    private val desc: String
) : BaseEnum {

    TERM("UT", "Term"),
    UI_MESSAGE("UM", "UI Message"),
    SERVER_MESSAGE("SM", "Server Message");

    override fun getCode() = this.code
    override fun getDesc() = this.desc
}