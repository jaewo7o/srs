import http from '@/api/http'

export async function login({ loginId, password }) {
    return http.post('/signin', {
        loginId,
        password
    })
}

export async function reissueAccessToken() {
    const response = await http.post('/reissue-token')
    localStorage.setItem('accessToken', response.data.accessToken)
}
