package com.jaewoo.srs.core.util

import com.jaewoo.srs.core.security.properties.SecurityProperties
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

@Component
class CookieUtil(
    private val securityProperties: SecurityProperties
) {
    fun createCookie(cookieName: String, value: String): Cookie {
        val token = Cookie(cookieName, value)
        token.setHttpOnly(true)
        token.setMaxAge(securityProperties.accessTokenExpirationMiniute.toInt())
        token.setPath("/")
        return token
    }

    fun getCookie(req: HttpServletRequest, cookieName: String): Cookie? {
        val cookies: Array<Cookie> = req.getCookies() ?: return null
        return cookies.find { cookie ->
            cookie.name.equals(cookieName)
        }
    }
}