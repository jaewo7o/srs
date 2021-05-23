import http from '@/api/http'

export function getMessages(search) {
    return http.get('/messages', { params: search })
}
