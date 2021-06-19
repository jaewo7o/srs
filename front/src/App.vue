<template>
    <div>
        <router-view></router-view>
        <vue-progress-bar></vue-progress-bar>
    </div>
</template>

<script>
export default {
    name: 'App',
    created() {
        // log environment
        if (process.env.NODE_ENV === 'development') {
            console.log(
                `@ Process Environment \n` +
                    `# Node Env        : ${process.env.NODE_ENV} (as available vue devtools)\n` +
                    `# API URL(Server) : ${process.env.VUE_APP_API_URL}\n`
            )
        }
    }
}
</script>
