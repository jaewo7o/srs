import http from '@/api/http'

const codes = {}

export async function getCodes(groupCode) {
    if (codes[groupCode]) {
        return resolveCodes(codes[groupCode])
    }

    const result = await http.get(`/group-codes/${groupCode}/codes`)
    codes[groupCode] = result.data || []

    return resolveCodes(codes[groupCode])
}

function resolveCodes(codes, languageCode) {
    return codes.map((code) => {
        return {
            value: code.code,
            text: languageCode === 'en' ? code.codeNameEn : code.codeNameKo
        }
    })
}
