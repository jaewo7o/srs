<template>
    <v-container fill-height style="max-width: 450px">
        <v-row align-center>
            <v-col>
                <v-alert :value="isLoginFail" type="error">
                    아이디와 패스워드를 확인주세요.
                </v-alert>
                <v-alert :value="isLogin" type="success">
                    로그인이 성공하였습니다.
                </v-alert>
                <v-card>
                    <v-toolbar flat>
                        <v-toolbar-title>Sign In</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-4">
                        <v-text-field
                            v-model="form.loginId"
                            label="이메일주소를 입력하세요."
                            prepend-icon="mdi-email"
                        />
                        <v-text-field
                            v-model="form.password"
                            type="password"
                            label="패스워드를 입력하세요."
                            prepend-icon="mdi-lock-outline"
                        />
                        <v-btn
                            color="primary"
                            depressed
                            large
                            block
                            @click="onClickLogin"
                        >
                            Sign In
                        </v-btn>
                        <router-link style="margin-top: 50px" to="/signUp">
                            회원가입하기
                        </router-link>
                    </div>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    name: 'SignIn',
    data() {
        return {
            form: {
                loginId: 'jeawoo.jeong@gmail.com',
                password: '1234'
            }
        }
    },
    computed: {
        ...mapState('auth', ['isLogin', 'isLoginFail'])
    },
    methods: {
        ...mapActions('auth', ['signIn']),
        onClickLogin: async function () {
            this.signIn({
                loginId: this.form.loginId,
                password: this.form.password
            })
        }
    }
}
</script>
