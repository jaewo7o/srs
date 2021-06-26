export default {
    namespaced: true,
    state: {
        languageCode: 'ko'
    },
    mutations: {
        setLanguageCode(state, languageCode) {
            state.languageCode = languageCode
        }
    },
    getters: {
        getLanguageCode() {
            return this.state.languageCode
        }
    }
}
