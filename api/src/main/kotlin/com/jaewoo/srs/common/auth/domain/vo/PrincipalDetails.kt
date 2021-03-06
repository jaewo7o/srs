package com.jaewoo.srs.common.auth.domain.vo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class PrincipalDetails(
    private val sessionUser: SessionUser
) : UserDetails, Serializable {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(
            SimpleGrantedAuthority("DEFAULT")
        )
    }

    override fun getPassword() = sessionUser.password

    override fun getUsername() = sessionUser.loginId

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true
}