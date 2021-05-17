<template>
    <div id="profile" style="text-align:center; margin-top:20px;">
        <div id="follows">
            <div style="margin:20px auto;">
                <a  v-if="this.$route.path != curPath" @click="$router.push('/user/userinfo')">
                    <Gear type="button" style="float:right"></Gear>
                </a>
                <a v-else>
                    <Gear type="button" style="float:right"></Gear>
                </a>
                <br><br>
                <a>
                    <img class="profileimg"   @click="goblog"  :src="profileImg" style=" border:2px solid black" />
                </a>
               <h1>{{this.$store.state.nickname}}</h1>
            </div>
            <div>
                <v-list-item v-if="this.$store.state.isKakaoLogin == 0" link v-on:click="goLogout" style="margin-left:20px; text-align:left;">
                    <v-list-item-action>
                        <v-icon>mdi-logout</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title class="doHyeon">로그아웃</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>  
                <v-list-item v-else  @click.prevent="Logout" style="margin-left:20px; text-align:left;">
                    <v-list-item-action>
                        <v-icon>mdi-logout</v-icon>
                    </v-list-item-action>
                    <v-list-item-content style="float:right">
                        <v-list-item-title class="doHyeon">로그아웃(K)
                        </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>    
                <hr>
            <v-list-item @click="gofollows(2)" style="margin-left:20px;">
                <v-list-item-action>
                    <i class="far fa-user"></i>
                </v-list-item-action>
                <v-list-item-content class="doHyeon">
                    팔로워 : {{countfollowers}}
                </v-list-item-content>
            </v-list-item>
            <v-list-item @click="gofollows(1)" style="margin-left:20px;">
                <v-list-item-action>
                    <i class="far fa-user"></i>
                </v-list-item-action>
                <v-list-item-content class="doHyeon">
                    팔로잉 : {{countfollowings}}
                </v-list-item-content>
            </v-list-item>
            </div>
        </div>
    </div>
</template>

<script>
    import kakaoLogin from "vue-kakao-login";
    import {onSuccess2, onFailure, GetMe, Logout } from "@/utils/Kakao.js";
    import Vue from 'vue';
    import Vuex from 'vuex';
    import {instance, instanceWithAuth} from '@/api/index'
    import {deleteCookie} from '@/utils/cookies'
    import Gear from '@/components/Gear'
    export default {
        components: {
            kakaoLogin,
            Gear,
        },
        created (){
            if(this.$store.state.uid!='') this.nickname = this.$store.state.nickname;
            this.getFollows();
            if(this.$store.state.isKakaoLogin == 0)
                this.getDetail();
        },
        watch: {
            '$route' (to, from) { 
                this.getFollows();
            }
        },
        computed :{
          countfollowers (){
            //   //console.log('computed - 팔로워');
              return this.$store.state.myfollowers
          },
          countfollowings () {
            //   //console.log('computed - 팔로잉');
              return this.$store.state.myfollowings
          }
        },
        methods: {
            onSuccess2,
            onFailure,
            GetMe,
            Logout,
            goJoin(){
                this.$emit('closeModal');
                if(this.$route.path!=='/') 
                    this.$router.push('/');
            },
             getDetail() {
                instanceWithAuth
                .get('user/account/userinfo', {})
                .then((response) => {
                    this.profileImg = response.data.object.profile
                    
                    this.$store.state.profile = this.profile;
                    this.userImage = this.profile;
                })
                .catch((error) => {
                    alert('이미지 로딩 실패' + error);
                });
            },
            async goLogout(){
                this.$store.commit('clearNickname')
                this.$store.commit('clearToken')
                this.$store.commit('clearUid')
                deleteCookie("nickname")
                deleteCookie("auth")
                deleteCookie("uid")
                this.$store.state.isKakaoLogin = 0;
                alert("로그아웃 되었습니다.")
                if(this.$route.path!=='/') 
                    this.$router.push('/');
            },
            gofollows(type) {
                // //console.log("gofollows"+type)
               this.$store.commit('followingtype',type)
                if(this.$route.path!=='/user/follows/'+this.$store.state.uid) 
                    this.$router.push('/user/follows/'+this.$store.state.uid);
                // this.$router.push('/user/follows/'+ this.$store.state.uid);
            },
            goblog(){
                // //console.log('---Go My Blog---')
                // //console.log(`/blog/${this.$store.state.uid}`);
                this.$store.commit('getowner',this.$store.state.uid)
                // TODO
                if(this.$route.path !== `/blog/${this.$store.state.uid}`){
                    this.$router.push('/blog/'+ this.$store.state.uid);
                } else{

                }
            },
            getFollows(){
                instance
                    .get("user/followlist/", {
                        params: {
                            uid: this.$store.state.uid,
                             type : 2
                        }
                    })
                    .then((response) => {
                        this.followers =  response.data.object.length
                        this.$store.state.myfollowers = this.followers
                        // //console.log("프로필 팔로워:"+this.$store.state.myfollowers)
                    })
                    .catch((response) => {
                        alert("목록을 불러올 수 없어요!!");
                    });
                    instance
                    .get("user/followlist/", {
                        params: {
                            uid: this.$store.state.uid,
                             type : 1
                        }
                    })
                    .then((response) => {
                        this.following =  response.data.object.length
                        this.$store.state.myfollowings = this.following
                        //  //console.log("프로필 팔로잉:"+this.$store.state.myfollowings)
                    })
                    .catch((response) => {
                        alert("목록을 불러올 수 없어요!!");
                    });
          }
        },
        data :() =>{
            return {
                nickname : "비회원",
                following: 0,
                followers:0,
                userImage:'',
                kakao_api_key : process.env.VUE_APP_KAKAO_ID,
                curPath : "/user/userinfo",
                url: `${process.env.VUE_APP_API_URL}`,
                profileImg:'',
            }
        }

    }
</script>

<style scoped>
.profileimg {
    height: 100px;
    width: 100px;
    border-radius: 150px;
    background-color: blueviolet;
    margin-top:10px;
    margin-bottom:10px;
}
#btnset{
    float:right;
    margin-right:20px;
    cursor: pointer;
}
</style>