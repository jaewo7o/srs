import http from '@/api/http'

export function getMessages(search) {
    return http.get('/messages', { params: search })
}

export function deleteMessage(id) {
    return http.delete(`/messages/${id}`)
}
