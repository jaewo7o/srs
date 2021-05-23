import axios from 'axios'
import { reissueAccessToken } from './auth'

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
    timeout: 3000
})

instance.interceptors.request.use(
    async function (config) {
        // Do something before request is sent
        config.headers['X-AUTH-ACCESS-TOKEN'] =
            localStorage.getItem('accessToken') || ''
        config.headers['X-AUTH-REFRESH-TOKEN'] =
            localStorage.getItem('refreshToken') || ''

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
            return response
        } catch (err) {
            console.error(
                '[_axios.interceptors.response] response : ' + err.message
            )
        }
    },
    async function (error) {
        try {
            let errorStatus = error.response.status
            const errorAPI = error.config

            if (errorStatus == '401') {
                if (errorAPI.retry) {
                    errorAPI.retry = true
                    await reissueAccessToken()
                    return await instance(errorAPI)
                } else {
                    alert('인증에 실패했습니다.')
                }
            }
            if (errorStatus == '403') alert('권한이 없습니다.')
            if (errorStatus == '406') alert(error.response.data.message)
            if (errorStatus == '500') alert('서버에서 오류가 발생하였습니다.')

            return error.response
        } catch (err) {
            console.error(
                '[_axios.interceptors.response] error : ' + err.message
            )
        }
    }
)

export default instance
