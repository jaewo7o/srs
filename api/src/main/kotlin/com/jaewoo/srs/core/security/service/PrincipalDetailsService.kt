package com.jaewoo.srs.core.security.service

import com.jaewoo.srs.app.user.dao.UserRepository
import com.jaewoo.srs.common.auth.domain.vo.PrincipalDetails
import com.jaewoo.srs.core.exception.SrsDataNotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Cacheable(value = ["user-session"], key="#loginId")
    override fun loadUserByUsername(loginId: String): UserDetails {
        val findUser = userRepository.findByLoginId(loginId).orElseThrow{SrsDataNotFoundException()}
        return PrincipalDetails(findUser)
    }
}