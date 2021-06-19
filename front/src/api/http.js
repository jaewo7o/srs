/* eslint-disable prettier/prettier */
import axios from 'axios'
import { reissueAccessToken } from './auth'
import router from '../router'

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    timeout: 3000
})

instance.interceptors.request.use(
    async function (config) {
        // Do something before request is sent
        config.headers['X-AUTH-ACCESS-TOKEN'] = localStorage.getItem('accessToken') || ''
        config.headers['X-AUTH-REFRESH-TOKEN'] = localStorage.getItem('refreshToken') || ''

        return config
    },
    function (error) {
        // Do something with request error
        return Promise.reject(error)
    }
)

// Add a response interceptor
instance.interceptors.response.use(
    function (response) {
        try {
            // Do something with response data
            return response.data.body
        } catch (err) {
            console.error('[_axios.interceptors.response] response : ' + err.message)
        }
    },
    async function (error) {
        try {
            const res = error.response
            const body = res.body
            let errorCode = res.status
            const errorAPI = error.config

            if (errorCode === 401) {
                if (errorAPI.retry) {
                    errorAPI.retry = true
                    await reissueAccessToken()
                    return await instance(errorAPI)
                } else {
                    alert('인증에 실패했습니다.')
                    router.push({ name: 'signIn' })
                }
            }
            if (errorCode === 403) alert('권한이 없습니다.')
            if (errorCode === 406) alert(body.data.error.message)
            if (errorCode === 500) alert('서버에서 오류가 발생하였습니다.')

            return body.data
        } catch (err) {
            console.error('[_axios.interceptors.response] error : ' + err.message)
        }
    }
)

export default instance
