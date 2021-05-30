<template>
    <v-container fill-height style="max-width: 600px">
        <v-row align-center>
            <v-col>
                <v-card>
                    <v-card-title>회원 가입하기</v-card-title>
                    <v-card-text class="pa-4">
                        <validation-observer
                            ref="observer"
                            v-slot="{ isInvalid }"
                        >
                            <v-form @submit.prevent="onClickSignUp">
                                <validation-provider
                                    name="이름"
                                    rules="max:10"
                                    v-slot="{ errors }"
                                >
                                    <v-text-field
                                        v-model="form.name"
                                        label="이름"
                                        prepend-icon="mdi-account"
                                        :counter="10"
                                        :error-messages="errors"
                                    />
                                </validation-provider>
                                <validation-provider
                                    name="핸드폰번호"
                                    :rules="{
                                        required: true,
                                        numeric: true,
                                        digits: 11
                                    }"
                                    v-slot="{ errors }"
                                >
                                    <v-text-field
                                        v-model="form.mobileNo"
                                        label="핸드폰번호"
                                        prepend-icon="mdi-cellphone"
                                        :counter="11"
                                        :error-messages="errors"
                                    />
                                </validation-provider>
                                <validation-provider
                                    name="이메일주소"
                                    :rules="{
                                        required: true,
                                        email: true
                                    }"
                                    v-slot="{ errors }"
                                >
                                    <v-text-field
                                        v-model="form.loginId"
                                        label="이메일주소"
                                        prepend-icon="mdi-email"
                                        :error-messages="errors"
                                    />
                                </validation-provider>
                                <v-text-field
                                    v-model="form.password"
                                    type="password"
                                    label="패스워드"
                                    prepend-icon="mdi-lock-outline"
                                />
                                <v-text-field
                                    v-model="form.password"
                                    type="password"
                                    label="패스워드 확인"
                                    prepend-icon="mdi-lock-outline"
                                />
                                <v-btn
                                    type="submit"
                                    color="primary"
                                    depressed
                                    large
                                    class="mr-2"
                                    @click="onClickSignUp"
                                    :disabled="isInvalid"
                                >
                                    가입하기
                                </v-btn>
                                <v-btn depressed large @click="onClickClear">
                                    초기화
                                </v-btn>
                            </v-form>
                        </validation-observer>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { signUp } from '@/api/user'
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

// extend('max', (value, param) => {
//     const limit = param[0]
//     if (value && value.length > limit) {
//         return `해당 필드는 ${limit}자를 초과할 수 없습니다.`
//     }
//     return true
// })

export default {
    name: 'SignUp',
    components: { ValidationObserver, ValidationProvider },
    data() {
        return {
            form: {
                name: '정테스트',
                mobileNo: '01022223333',
                loginId: 'test@gmail.com',
                password: '1234'
            }
        }
    },
    methods: {
        onClickSignUp: async function () {
            const isValid = await this.$refs.observer.validate()

            if (isValid) {
                const response = await signUp(this.form)
                console.log(response)
                if (response.status === 200) {
                    this.$router.push({ name: 'signIn' })
                }
            }
        },
        onClickClear: function () {
            this.form.name = null
            this.form.mobileNo = null
            this.form.loginId = null
            this.form.password = null
        }
    }
}
</script>
