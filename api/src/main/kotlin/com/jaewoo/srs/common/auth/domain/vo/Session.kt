package com.jaewoo.srs.common.auth.domain.vo

data class Session(
    val name: String,
    val mobileNo: String,
    val loginId: String,
    val password: String
)
