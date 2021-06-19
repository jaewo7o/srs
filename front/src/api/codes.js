import http from '@/api/http'

export async function getCodes(groupCode) {
    const result = await http.get(`/group-codes/${groupCode}/codes`)
    return result.data || []
}
