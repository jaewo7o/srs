<template>
    <v-container fill-height style="max-width: 450px">
        <v-row align-center>
            <v-col>
                <v-card>
                    <v-toolbar flat>
                        <v-toolbar-title>회원 가입하기</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-4">
                        <v-text-field
                            v-model="form.name"
                            label="이름"
                            prepend-icon="mdi-account"
                        />
                        <v-text-field
                            v-model="form.mobileNo"
                            label="핸드폰번호"
                            prepend-icon="mdi-cellphone"
                        />
                        <v-text-field
                            v-model="form.loginId"
                            label="이메일주소"
                            prepend-icon="mdi-email"
                        />
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
                            color="primary"
                            depressed
                            large
                            block
                            @click="onClickSignUp"
                        >
                            가입하기
                        </v-btn>
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
                mobileNo: '010-2222-3333',
                loginId: 'test@gmail.com',
                password: '1234'
            }
        }
    },
    methods: {
        onClickSignUp: async function () {
            const response = await signUp(this.form)

            if (response.status === 200) {
                this.$router.push({ name: 'signIn' })
            }
        }
    }
}
</script>
