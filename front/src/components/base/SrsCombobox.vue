<template>
    <validation-provider :name="label" :rules="rules" v-slot="{ errors }">
        <label>
            {{ label }}
            <v-combobox
                :dense="dense"
                :outlined="outlined"
                label=""
                :solo="solo"
                v-bind="attrs"
                v-on="$listeners"
                :error-messages="errors"
            ></v-combobox>
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
        solo: {
            type: Boolean,
            default: true
        },
        rules: {
            type: Object,
            default: null
        }
    },
    computed: {
        attrs() {
            //console.log('attr start')
            //console.log(this.$attrs)
            //let valueObject = this.$attrs.value
            // eslint-disable-next-line vue/no-side-effects-in-computed-properties
            //this.$attrs['value'] = valueObject.value
            //console.log(this.$attrs)
            //console.log('attr end')
            return this.$attrs
        }
    }
}
</script>
