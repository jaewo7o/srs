import * as authApi from '@/api/auth'
import router from '../router'

export default {
    namespaced: true,
    state: {
        user: null,
        isLogin: false,
        isLoginFail: false
    },
    mutations: {
        setLoginSuccess(state, user) {
            state.isLogin = true
            state.isLoginFail = false
            state.user = user
        },
        setLoginFail(state) {
            state.isLogin = false
            state.isLoginFail = true
        },
        setLogout(state) {
            state.isLogin = false
            state.isLoginFail = false
        }
    },
    actions: {
        async signIn({ commit }, { loginId, password }) {
            const response = await authApi.login({ loginId, password })

            if (response.status === 200) {
                localStorage.setItem(
                    'accessToken',
                    response.data.token.accessToken
                )

                localStorage.setItem(
                    'refreshToken',
                    response.data.token.refreshToken
                )

                commit('setLoginSuccess', response.data.user)
                router.push({ name: 'home' })
            } else {
                commit('setLoginFail')
            }
        },
        logout({ commit }) {
            commit('setLogout')
            router.push({ name: 'signIn' })
        }
    }
}
