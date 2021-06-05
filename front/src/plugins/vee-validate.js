import Vue from 'vue'

import { extend, ValidationProvider, ValidationObserver } from 'vee-validate'
import { max, required, numeric, digits, email } from 'vee-validate/dist/rules'
extend('max', {
    ...max,
    message: `{_field_}필드는 {length}자를 초과할 수 없습니다.`
})
extend('required', {
    ...required,
    message: `{_field_}필드는 필수값입니다.`
})
extend('numeric', {
    ...numeric,
    message: `{_field_}필드는 숫자만 입력 가능합니다.`
})
extend('digits', {
    ...digits,
    message: `{_field_}필드는 {length}자리여야 합니다.`
})
extend('email', {
    ...email,
    message: `잘못입력된 이메일주소입니다.`
})

Vue.component('ValidationProvider', ValidationProvider)
Vue.component('ValidationObserver', ValidationObserver)
