package com.jaewoo.srs.app.user

import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.common.code.domain.entity.GroupCode
import com.jaewoo.srs.common.message.domain.entity.Message

fun buildUser(
    name: String = "name",
    mobileNo: String = "mobileNo",
    loginId: String = "jeawoo.jeong@gmail.com",
    password: String = "1234"
) = User(
    name = name,
    mobileNo = mobileNo,
    loginId = loginId,
    password = password
)

fun buildMessage(
    key: String = "key",
    contentsKo: String = "contentsKo",
    contentsEn: String = "contentsEn",
) = Message(
    key = key,
    contentsKo = contentsKo,
    contentsEn = contentsEn
)

fun buildGroupCode(
    groupCode: String = "groupCode",
    groupCodeNameKo: String = "groupCodeNameKo",
    groupCodeNameEn: String = "groupCodeNameEn"
) = GroupCode(
    groupCode = groupCode,
    groupCodeNameKo = groupCodeNameKo,
    groupCodeNameEn = groupCodeNameEn
)