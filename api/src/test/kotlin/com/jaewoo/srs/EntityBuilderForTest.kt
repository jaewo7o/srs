package com.jaewoo.srs.app.user

import com.jaewoo.srs.app.user.domain.entity.User
import com.jaewoo.srs.common.code.domain.entity.Code
import com.jaewoo.srs.common.code.domain.entity.GroupCode
import com.jaewoo.srs.common.message.domain.entity.Message
import com.jaewoo.srs.common.message.domain.enum.MessageType

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
    messageType: MessageType = MessageType.SERVER_MESSAGE,
    contentsKo: String = "contentsKo",
    contentsEn: String = "contentsEn",
) = Message(
    key = key,
    messageType = messageType,
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


fun buildCode(
    groupCode: String = "groupCode",
    code: String = "code",
    codeNameKo: String = "codeNameKo",
    codeNameEn: String = "codeNameEn",
    sortRank: Int = 0
) = Code(
    groupCode = groupCode,
    code = code,
    codeNameKo = codeNameKo,
    codeNameEn = codeNameEn,
    sortRank = sortRank
)