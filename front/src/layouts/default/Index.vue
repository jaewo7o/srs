<template>
    <v-app>
        <default-app-bar @isDrawer="isDrawer = !isDrawer" />
        <default-drawer v-model="isDrawer" />
        <default-view />
        <default-bottom :title="footerTitle" />
    </v-app>
</template>

<script>
import DefaultAppBar from './AppBar'
import DefaultDrawer from './Drawer'
import DefaultView from './View'
import DefaultBottom from './Bottom'
import { mapActions, mapState } from 'vuex'

export default {
    name: 'DefaultLayout',
    components: { DefaultAppBar, DefaultDrawer, DefaultView, DefaultBottom },
    computed: {
        ...mapState('auth', ['isLogin'])
    },
    data() {
        return {
            title: 'SRS Admin',
            footerTitle: 'License By Jaewoo Jung',
            isDrawer: false
        }
    },
    methods: {
        ...mapActions('auth', ['logout'])
    }
}
</script>

<style></style>
