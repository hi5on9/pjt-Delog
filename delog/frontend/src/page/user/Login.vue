<template>
    <div class="user" id="login" style="height:280px;">
        <div class="wrapC table">
            <div class="middle">
                <p class="validation-text" style="text-align:center; color:red;">
                    <span v-if="!isUsernameValid && email">
                    아이디는 이메일 형식입니다!
                    </span>
                 </p>
                <div class="input-wrap" @submit.prevent="goLogin">
                    <input v-model="email" id="email" placeholder="이메일을 입력해주세요" type="text"/>
        
                </div>
                <div class="input-wrap">
                    <input
                        v-model="password"
                        type="password"
                        id="password"
                        placeholder="영문, 숫자 혼용 8자 이상" @keyup.enter ="goLogin"/>
                </div>

                <button v-on:click="goLogin" class="btn btn--back btn--login" style="margin-left:0px; font-size:1.1rem;">
                    로그인 하기
                </button>
                 <p class="log">{{ logMessage }}</p>
                <div class="add-option">
                    <div class="wrap">
                        <p style="margin-bottom:0px;">아직 회원이 아니신가요?</p>
                        <br>
                        <router-link v-on:click.native="goJoin" v-bind:to='{name:constants.URL_TYPE.USER.JOIN}' style="float:left; font-size:1rem;">
                            회원가입
                        </router-link>
                        <br><br>
                    </div>
                    <button @click="goJoin">
                        <kakao-login
                        :api-key=kakao_api_key
                        image="kakao_login_btn_small"
                        :on-success=onSuccess
                        :on-failure=onFailure 
                        style="cursor:pointer"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import kakaoLogin from "vue-kakao-login";
    import {onSuccess, onFailure, GetMe, Logout } from "@/utils/Kakao.js";
    import '@/assets/css/user.scss'
    import constants from '@/lib/constants'
    import Modal from '@/components/Modal.vue'
    import Vue from 'vue'
    import Vuex from 'vuex'
    import {mapState, mapGetters, mapMutations, mapActions} from 'vuex'
    import { validateEmail } from '@/utils/validation';
    import {
    getAuthFromCookie,
    getNicknameFromCookie,
    getUidFromCookie,
    saveAuthToCookie,
    saveNicknameToCookie,
    saveUidToCookie,
} from '@/utils/cookies';
    import {instance, instanceWithAuth} from '@/api/index';

    export default {
        
         data() {
            return {
            // form values
            constants,
            username: '',
            email : '',
            password: '',
            // log
            logMessage: '',
            kakao_api_key : process.env.VUE_APP_KAKAO_ID,
            info :'',
            flag : false,
            };
        },
        computed: {
            isUsernameValid() {
            return validateEmail(this.email);
            },
        },
        components: {
            kakaoLogin,
        },
        created() {
            
        },
        watch: {},
        methods: {
            onSuccess,
            onFailure,
            GetMe,
            Logout,
            goJoin(){
                this.$emit('closeModal')
            },
            goLogin: function () {
                if (!this.email) {
                    alert('이메일을 입력해주세요.');
                } else if (!this.password) {
                    alert('비밀번호를 입력해주세요.');
                } else {
                    this.createHandler();
                }
            },
         
            async createHandler() {
                 instance.get('user/login', {
                        params: {
                            Email: this.email,
                            Password: this.password
                        }
                    })
                    .then( async (response) => {
                        if(response.data.object == null) {
                            alert('아이디나 비밀번호가 잘못되었습니다.')
                        }else{
                       
                       await this.checkReport(this.email);
                        if(this.flag == true){
                            const token = response.headers['jwt_auth_token'];
                            const nickname = response.data.object.nickname;
                            const uid = response.data.object.uid;
                            this.$store.commit('setToken', token)
                            this.$store.commit('setNickname', nickname);
                            this.$store.commit('setUid', uid);
                            saveAuthToCookie(token);
                            saveNicknameToCookie(nickname);
                            saveUidToCookie(uid);
                            this.$emit('closeModal')
                            this.$store.state.isKakaoLogin = 0;
                            if(this.$route.path!=='/') 
                                this.$router.push('/');
                            }
                        }
                    })
                    .catch( (response) => {
                        alert("로그인 시도중 에러가 발생했습니다.")
                    });
            },
            goToPages() {
                this.showModal = !this.showModal; ////
            },

            checkReport(email) {
                instance
                .delete('report/check',{
                    
                })
                .then((response) => {
                    this.flag = false;
                    this.findPrison(email);
                })
                .catch( (response) => {
                        alert('delete 중 에러가 발생했습니다.')
                    });
            },
            findPrison(email) {
                instance
                .post('report/check/userprison', {
                    email : email,
                })
                .then((response) => {
                    if(response.data.object){
                        alert("정지당한 계정입니다. " + (response.data.object.term+1) + "분 후에 로그인해주세요.")
                    }else{
                        this.flag = true;
                    }
                })
                .catch( (response) => {
                        alert('정지계정 찾는중 에러가 발생했습니다.')
                    });
            }
        },
    }
</script>
<style scoped>
/* *{
    background-color:black;
} */
</style>