import Vue from 'vue'
import Vuex from 'vuex'

import auth from './auth'
import system from './system'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {},
    mutations: {},
    actions: {},
    modules: {
        auth,
        system
    }
})
