<template>
    <v-navigation-drawer app v-bind="$attrs">
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title class="title">SRS Menu</v-list-item-title>
                <v-list-item-subtitle>Admin</v-list-item-subtitle>
            </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list dense nav>
            <v-list-item v-for="item in items" :key="item.title" link router :to="{ name: item.link }">
                <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>
                        {{ item.title }}
                    </v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
export default {
    name: 'DefaultDrawer',
    data() {
        return {
            items: [
                {
                    title: 'Dashboard',
                    icon: 'mdi-view-dashboard',
                    link: 'home'
                },
                { title: 'About', icon: 'mdi-help-box', link: 'about' },
                { title: 'Message', icon: 'mdi-view-list', link: 'message' }
            ]
        }
    }
}
</script>

<style></style>
