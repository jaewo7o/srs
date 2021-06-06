package com.jaewoo.srs.common.code.service

import com.jaewoo.srs.common.code.dao.CommonGroupCodePredicator
import com.jaewoo.srs.common.code.dao.CommonGroupCodeRepository
import com.jaewoo.srs.common.code.dao.CommonGroupCodeRepositorySupport
import com.jaewoo.srs.common.code.domain.dto.CreateCommonGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.SearchCommonGroupCodeRequest
import com.jaewoo.srs.common.code.domain.dto.UpdateCommonGroupCodeRequest
import com.jaewoo.srs.common.code.domain.entity.CommonGroupCode
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import com.jaewoo.srs.core.exception.SrsRuntimeException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CommonGroupCodeService(
    private val commonGroupCodeRepository: CommonGroupCodeRepository,
    private val commonGroupCodeRepositorySupport: CommonGroupCodeRepositorySupport
) {
    fun searchCommonGroupCodesPageable(dto: SearchCommonGroupCodeRequest, pageable: Pageable): Any {
        val predicate = CommonGroupCodePredicator()
            .groupCodeName(dto.groupCodeName)
            .value()

        return commonGroupCodeRepositorySupport.findAllPage(predicate, pageable)
    }

    fun getCommonGroupCode(groupCode: String)
        = commonGroupCodeRepository.findById(groupCode).orElseThrow { SrsDataNotFoundException() }

    fun updateCommonGroupCode(groupCode: String, dto: UpdateCommonGroupCodeRequest): CommonGroupCode {
        val commonGroupCode = getCommonGroupCode(groupCode).also {
            it.groupCodeNameKo = dto.groupCodeNameKo
            it.groupCodeNameEn = dto.groupCodeNameEn
        }

        return commonGroupCodeRepository.save(commonGroupCode)
    }

    fun createCommonGroupCode(dto: CreateCommonGroupCodeRequest): CommonGroupCode {
        val isExist = commonGroupCodeRepository.existsById(dto.groupCode)
        if (isExist) {
            throw SrsRuntimeException("MSG0006")
        }

        return commonGroupCodeRepository.save(dto.toEntity())
    }
}