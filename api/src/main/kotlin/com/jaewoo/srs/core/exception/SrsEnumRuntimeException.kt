package com.jaewoo.srs.core.exception

import com.jaewoo.srs.core.exception.code.ErrorCode

class SrsEnumRuntimeException(val errorCode: ErrorCode) : Exception()