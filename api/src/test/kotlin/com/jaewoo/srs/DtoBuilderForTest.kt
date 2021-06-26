package com.jaewoo.srs.app.user

import com.jaewoo.srs.app.user.domain.dto.CreateUserRequest
import com.jaewoo.srs.common.code.domain.dto.CreateCodeRequest
import com.jaewoo.srs.common.code.domain.dto.CreateGroupCodeRequest

fun buildCreateUserRequest(
    name: String = "name",
    mobileNo: String = "mobileNo",
    languageCode: String = "ko",
    timezoneName: String = "Asia/Seoul",
    loginId: String = "testuser@gmail.com",
    password: String = "1234"
) = CreateUserRequest(
    name = name,
    mobileNo = mobileNo,
    languageCode = languageCode,
    timezoneName = timezoneName,
    loginId = loginId,
    password = password
)

fun buildCreateGroupCodeRequest(
    groupCode: String = "groupCode",
    groupCodeNameKo: String = "groupCodeNameKo",
    groupCodeNameEn: String = "groupCodeNameEn"
) = CreateGroupCodeRequest(
    groupCode = groupCode,
    groupCodeNameKo = groupCodeNameKo,
    groupCodeNameEn = groupCodeNameEn
)

fun buildCreateCodeRequest(
    code: String = "code",
    codeNameKo: String = "codeNameKo",
    codeNameEn: String = "codeNameEn",
    sortRank: Int = 0
) = CreateCodeRequest(
    code = code,
    codeNameKo = codeNameKo,
    codeNameEn = codeNameEn,
    sortRank = sortRank
)