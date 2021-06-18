package com.jaewoo.srs.core.context

import com.jaewoo.srs.common.auth.domain.vo.Session

class SrsContext {
    companion object {
        private val threadLocal = ThreadLocal<Session>()

        fun setSession(session: Session) = threadLocal.set(session)
        fun getSession() = threadLocal.get()
    }
}