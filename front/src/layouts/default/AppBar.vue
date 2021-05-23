<template>
    <v-app-bar app color="primary" dark fixed>
        <v-app-bar-nav-icon @click="$emit('isDrawer')" />
        <v-toolbar-title>{{ title }}</v-toolbar-title>
        <v-spacer />
        <v-toolbar-items class="hidden-sm-and-down">
            <v-btn text v-if="isLogin" @click="logout">Logout</v-btn>
            <v-btn text v-else router :to="{ name: 'signIn' }">SignIn</v-btn>
        </v-toolbar-items>
    </v-app-bar>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
    name: 'DefaultAppBar',
    computed: {
        ...mapState('auth', ['isLogin'])
    },
    data() {
        return {
            title: 'SRS Admin'
        }
    },
    methods: {
        ...mapActions('auth', ['logout'])
    }
}
</script>

<style></style>
