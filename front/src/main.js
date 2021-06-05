import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import './plugins'

Vue.config.productionTip = false

Vue.config.errorHandler = (e) => {
    console.log('error handler!!!')
    console.log(e.message)
}

new Vue({
    router,
    store,
    vuetify,
    render: (h) => h(App)
}).$mount('#app')
