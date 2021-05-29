import http from '@/api/http'

export function signUp(form) {
    return http.post('/anonymous/users', form)
}
