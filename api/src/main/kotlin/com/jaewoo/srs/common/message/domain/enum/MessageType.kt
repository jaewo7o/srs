package com.jaewoo.srs.common.message.domain.enum

import com.fasterxml.jackson.annotation.JsonValue
import com.jaewoo.srs.core.enumerate.BaseEnum

enum class MessageType(
    private val code: String,
    private val desc: String
) : BaseEnum {

    TERM("UT", "Term"),
    UI_MESSAGE("UM", "UI Message"),
    SERVER_MESSAGE("SM", "Server Message");

    @JsonValue
    override fun getCode() = this.code
    override fun getDesc() = this.desc

//    companion object {
//        @JsonCreator
//        fun fromString(code: String): MessageType {
//            return when (code) {
//                "UI" -> TERM
//                "UM" -> UI_MESSAGE
//                "SM" -> SERVER_MESSAGE
//                else -> throw IllegalArgumentException("Not supported status $code")
//            }
//        }
//    }
}