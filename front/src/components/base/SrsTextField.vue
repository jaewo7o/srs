<template>
    <validation-provider :name="label" :rules="rules" v-slot="{ errors }">
        <label>
            {{ label }}
            <v-text-field
                class="mt-1"
                :outlined="outlined"
                :dense="dense"
                v-bind="$attrs"
                v-on="$listeners"
                :error-messages="errors"
            ></v-text-field>
        </label>
    </validation-provider>
</template>

<script>
export default {
    name: 'SrsTextField',
    inheritAttrs: false,
    props: {
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
        },
        rules: {
            type: Object,
            default: null
        }
    }
}
</script>
