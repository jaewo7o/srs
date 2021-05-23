<template>
    <v-footer app color="primary" dark absolute>
        <v-col class="text-right" cols="12">
            {{ new Date().getFullYear() }} —
            <strong>{{ title }}</strong>
        </v-col>
    </v-footer>
</template>

<script>
export default {
    name: 'DefaultBottom',
    props: ['title']
}
</script>

<style></style>
