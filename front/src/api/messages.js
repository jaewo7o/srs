import http from '@/api/http'

export function getMessages(search) {
    return http.get('/messages', { params: search })
}

export function createMessage(message) {
    return http.post('/messages', message)
}

export function deleteMessage(id) {
    return http.delete(`/messages/${id}`)
}
