package com.jaewoo.srs.common.auth.domain.vo

data class SessionUser(
    val id: Long,
    val name: String,
    val mobileNo: String,
    val loginId: String,
    val password: String
)
