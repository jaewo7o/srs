import http from '@/api/http'

export function getCodes(groupCode) {
    const result = http.get(`/group-codes/${groupCode}/codes`)
    console.log(result)
    console.log('====>>1')
    console.log(result.data)
    return result.data || []
}
