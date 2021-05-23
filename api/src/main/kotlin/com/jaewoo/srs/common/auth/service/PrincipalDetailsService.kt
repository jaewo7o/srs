package com.jaewoo.srs.common.auth.service

import com.jaewoo.srs.app.user.dao.UserRepository
import com.jaewoo.srs.common.auth.domain.vo.PrincipalDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(loginId: String): UserDetails {
        val findUser = userRepository.findByLoginId(loginId).orElseThrow()
        return PrincipalDetails(findUser)
    }
}