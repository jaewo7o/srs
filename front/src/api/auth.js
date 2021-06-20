import http from '@/api/http'

export async function login({ loginId, password }) {
    return await http.post('/signin', {
        loginId,
        password
    })
}

export async function reissueAccessToken() {
    let config = {
        headers: {
            'X-AUTH-REFRESH-TOKEN': localStorage.getItem('refreshToken') || ''
        }
    }

    const response = await http.post('/reissue-token', null, config)

    localStorage.setItem('accessToken', response.data.accessToken)
}
