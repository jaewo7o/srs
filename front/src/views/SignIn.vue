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
                        <validation-observer
                            ref="observer"
                            v-slot="{ isInvalid }"
                        >
                            <form @submit.prevent="onClickLogin">
                                <validation-provider
                                    name="이메일주소"
                                    rules="required"
                                    v-slot="{ errors }"
                                >
                                    <v-text-field
                                        v-model="form.loginId"
                                        label="이메일주소를 입력하세요."
                                        prepend-icon="mdi-email"
                                        :error-messages="errors"
                                    />
                                </validation-provider>
                                <validation-provider
                                    name="패스워드"
                                    rules="required"
                                    v-slot="{ errors }"
                                >
                                    <v-text-field
                                        v-model="form.password"
                                        type="password"
                                        label="패스워드를 입력하세요."
                                        prepend-icon="mdi-lock-outline"
                                        :error-messages="errors"
                                    />
                                </validation-provider>
                                {{ isInvalid }}
                                <v-btn
                                    type="submit"
                                    color="primary"
                                    depressed
                                    large
                                    block
                                    :disabled="isInvalid"
                                >
                                    Sign In
                                </v-btn>
                            </form>
                        </validation-observer>
                        <div class="text-center mt-5">
                            <router-link class="text-decoration-none" to="/">
                                Home
                            </router-link>
                            |
                            <router-link
                                class="text-decoration-none"
                                to="/signUp"
                            >
                                회원가입하기
                            </router-link>
                        </div>
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
            const isValid = await this.$refs.observer.validate()

            if (isValid) {
                this.signIn({
                    loginId: this.form.loginId,
                    password: this.form.password
                })
            }
        }
    }
}
</script>
