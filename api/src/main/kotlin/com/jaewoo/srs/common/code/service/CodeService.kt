package com.jaewoo.srs.common.code.service

import com.jaewoo.srs.common.code.dao.CodeRepository
import com.jaewoo.srs.common.code.domain.dto.CreateCodeRequest
import com.jaewoo.srs.common.code.domain.dto.UpdateCodeRequest
import com.jaewoo.srs.common.code.domain.entity.Code
import com.jaewoo.srs.common.code.domain.entity.CodeKey
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.exception.SrsRuntimeException
import org.springframework.stereotype.Service

@Service
class CodeService(
    private val codeRepository: CodeRepository
) {

    fun getCode(groupCode: String, code: String)
        = codeRepository.findById(CodeKey(groupCode, code)).orElseThrow { SrsDataNotFoundException() }

    fun updateCode(groupCode: String, code: String, dto: UpdateCodeRequest): Code {
        val codeEntity = getCode(groupCode, code).also {
            it.codeNameKo = dto.codeNameKo
            it.codeNameEn = dto.codeNameEn
            it.sortRank = dto.sortRank
        }

        return codeRepository.save(codeEntity)
    }

    fun createCode(dto: CreateCodeRequest): Code {
        val isExist = codeRepository.existsById(CodeKey(dto.groupCode, dto.code))
        if (isExist) {
            throw SrsRuntimeException("MSG0006")
        }

        return codeRepository.save(dto.toEntity())
    }
}