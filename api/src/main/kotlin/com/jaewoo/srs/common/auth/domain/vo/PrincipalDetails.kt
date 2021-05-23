package com.jaewoo.srs.common.auth.domain.vo

import com.jaewoo.srs.app.user.domain.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails(
    private val user: User
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(
            SimpleGrantedAuthority("DEFAULT")
        )
    }

    override fun getPassword() = user.password

    override fun getUsername() = user.loginId

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true
}