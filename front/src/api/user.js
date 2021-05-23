import http from '@/api/http'

export function signUp(form) {
    console.log('fundtion ===>')
    return http.post('/signup', form)
}
