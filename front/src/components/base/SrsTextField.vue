<template>
    <label>
        {{ label }}
        <v-text-field
            class="mt-1"
            :outlined="outlined"
            :dense="dense"
            v-bind="$attrs"
            v-on="$listeners"
        ></v-text-field>
    </label>
</template>

<script>
export default {
    name: 'SrsTextField',
    inheritAttrs: false,
    props: {
        value: {
            type: String,
            default: null
        },
        label: {
            type: String,
            default: null
        },
        outlined: {
            type: Boolean,
            default: true
        },
        dense: {
            type: Boolean,
            default: true
        }
    }
}
</script>
