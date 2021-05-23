package com.jaewoo.srs.app.user

import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest

fun buildCreateUserRequest(
    name: String = "name",
    mobileNo: String = "mobileNo",
    loginId: String = "testuser@gmail.com",
    password: String = "1234"
) = CreateUserRequest(
    name = name,
    mobileNo = mobileNo,
    loginId = loginId,
    password = password
)
