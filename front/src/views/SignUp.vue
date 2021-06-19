<template>
    <v-container fill-height style="max-width: 600px">
        <v-row align-center>
            <v-col>
                <v-card>
                    <v-card-title>회원 가입하기</v-card-title>
                    <v-card-text class="pa-4">
                        <validation-observer ref="observer" v-slot="{ isInvalid }">
                            <v-form @submit.prevent="onClickSignUp">
                                <srs-text-field
                                    v-model="form.name"
                                    label="이름"
                                    :rules="{
                                        required: true,
                                        max: 10
                                    }"
                                    :counter="10"
                                />
                                <srs-text-field
                                    v-model="form.mobileNo"
                                    label="핸드폰번호"
                                    :rules="{
                                        required: true,
                                        numeric: true,
                                        digits: 11
                                    }"
                                    :counter="11"
                                />
                                <srs-text-field
                                    v-model="form.loginId"
                                    label="이메일주소"
                                    :rules="{
                                        required: true,
                                        email: true
                                    }"
                                />
                                <srs-text-field v-model="form.password" type="password" label="패스워드" />
                                <srs-text-field v-model="form.password" type="password" label="패스워드 확인" />
                                <v-btn type="submit" color="primary" depressed large class="mr-2" :disabled="isInvalid">
                                    가입하기
                                </v-btn>
                                <v-btn depressed large @click="onClickClear">초기화</v-btn>
                            </v-form>
                        </validation-observer>
                    </v-card-text>

                    <div class="text-center mt-5">
                        <router-link class="text-decoration-none" to="/">Home</router-link>
                        |
                        <router-link class="text-decoration-none" to="/signIn">로그인페이지</router-link>
                    </div>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { signUp } from '@/api/user'

export default {
    name: 'SignUp',
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
                if (response.isSuccess) {
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
