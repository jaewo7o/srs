package com.jaewoo.srs.core.context

import com.jaewoo.srs.app.user.domain.entity.User

class SrsContext {
    companion object {
        private val userStore = ThreadLocal<User>()

        fun setUser(user: User) = userStore.set(user)
        fun getUser() = userStore.get()
    }
}