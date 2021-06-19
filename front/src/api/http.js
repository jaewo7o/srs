import axios from 'axios'
import { reissueAccessToken } from './auth'
import router from '../router'

// HTTP STATUS CODE
const BAD_REQUEST = 400
const UNAUTHORIZED = 401

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
            console.log('sucess in')
            console.log(response.data.body)
            console.log('#######')
            return response.data.body
        } catch (err) {
            console.error('[_axios.interceptors.response] response : ' + err.message)
        }
    },
    async function (error) {
        try {
            console.log('error in')
            if (error.response) {
                const { config, status, body } = error.response

                if (status === BAD_REQUEST) {
                    alert(body.data.error.message)
                } else if (status === UNAUTHORIZED) {
                    if (config.retry) {
                        config.retry = true
                        await reissueAccessToken()
                        return await instance(config)
                    } else {
                        localStorage.removeItem('accessToken')
                        localStorage.removeItem('refreshToken')

                        alert('인증이 만료되었습니다.')
                        router.push({ name: 'signIn' })
                    }
                } else if (status === 403) {
                    alert('권한이 없습니다.')
                } else if (status === 406) {
                    alert(body.data.error.message)
                } else if (status === 500) {
                    alert('서버에서 오류가 발생하였습니다.')
                }
            }
        } catch (err) {
            console.error('[_axios.interceptors.response] error : ' + err.message)
        }
    }
)

export default instance
