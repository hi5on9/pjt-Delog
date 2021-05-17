<template>
    <div>
        <profile/>
        <hr>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.ADMIN.MANAGE}">
            <v-list-item-action>
               <i class="fas fa-users-cog"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">게시글 및 회원 관리</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.ADMIN.STATS}">
            <v-list-item-action>
                <i class="fas fa-book-reader"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">사이트 통계</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.ADMIN.NOTICE}">
            <v-list-item-action>
                <i class="fas fa-exclamation"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">공지사항</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.ADMIN.ADMINREPORT}">
            <v-list-item-action>
                <i class="fas fa-angry"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">신고내역 확인</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
                <v-list-item link v-bind:to="{name:constants.URL_TYPE.NOTE.NOTE}">
            <v-list-item-action>
                <i class="fas fa-envelope"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">쪽지함</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
    </div>
</template>

<script>
    import Profile from './Profile.vue'
    import constants from '@/lib/constants'
    import {deleteCookie} from '@/utils/cookies'
    export default {
        data() {
           return {
               constants
           }
        },
        components: { 
            Profile,
        },
        methods: {
            async goLogout(){
                this.$store.commit('clearNickname')
                this.$store.commit('clearToken')
                this.$store.commit('clearUid')
                deleteCookie("nickname")
                deleteCookie("auth")
                deleteCookie("uid")
                this.$emit('closeModal')
                this.$store.state.isKakaoLogin = 0;
                alert("로그아웃 되었습니다.")
                if(this.$route.path!=='/') 
                    this.$router.push('/');
            },
        }
    }
</script>

<style>

</style>