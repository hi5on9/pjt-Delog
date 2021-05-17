<template>
    <div id="header" v-if="isHeader">
        <v-navigation-drawer v-model="drawer" app clipped temporary>
            <v-list dense v-if="this.$store.state.nickname=='' ">
                <v-list-item link v-on:click="goLogin" style="margin-left:20px;">
                    <v-list-item-action>
                        <v-icon>mdi-login</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title class="doHyeon" style="font-size:1.05rem;">로그인</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
            <v-list v-else-if="this.$store.state.uid==1">
                <admin-menu/>
            </v-list>
            <v-list v-else>
                <side-login/>
            </v-list>
            <v-switch :label="`Theme switch`" @click="changeTheme" style="margin-left: 45px;"></v-switch>
        </v-navigation-drawer>

       <!-- NAVBAR -->
        <v-app-bar app clipped-left>
            <v-col>
                <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
                <span>
                    <button v-on:click="goMain()" class="doHyeon" style="text-decoration:none; color:#777;">
                        De-log
                    </button>
                </span>
            </v-col>
            <div>
                <div v-if="nowUrl == 'writer'">
                    <div v-if="this.$store.state.login_state=='SAME'">
                        <header-detail-writer/>
                    </div>
                    <div v-else>
                        <header-detail-reader/>
                    </div>
                </div>
                <div v-else-if="nowUrl == 'postWrite'">
                    <span>
                        <div class="btn insertbtn" @click="tempInsert" style="width:80px;">
                            임시저장
                        </div>
                    </span>
                    <span>
                        <div class="btn insertbtn" @click="postInsert" style="width:80px;">
                            <span>등록 완료</span>
                        </div>
                    </span>
                    <span>
                        <div class="btn" @click="goSearch" style="width:40px;">
                            <i class="fas fa-search"></i>
                        </div>
                    </span>
                </div>
                <div v-else>
                    <v-list-item v-bind:to="{name:constants.URL_TYPE.POST.SEARCH}">
                        <i class="fas fa-search"></i>
                    </v-list-item>
                </div>
            </div>
        </v-app-bar>

        <!-- 로그인 모달 -->
        <LoginModal v-if="showModal" @close="showModal = false">
            <h3 slot="header">
                
                <span class="doHyeon" style="font-size:1.5rem; color:black;">로그인</span>
                <i class="closeModalBtn fas fa-times" @click="showModal = false" style="float:right; color:black; cursor:pointer"></i>
                <br><br>
                <Login v-on:closeModal="goLogin" ></Login>
            </h3>

            <div slot="body">
            </div>
        </LoginModal>
        <!-- <button class="button" @click="changeTheme" style="right:100px;">
            <div v-if="this.$vuetify.theme.dark == false">
                <i class="far fa-moon"></i>
                다크 모드로 변경
            </div>
            <div v-else>
                <i class="far fa-sun"></i>
                라이트 모드로 변경
            </div>
        </button> -->
        <button class="button" style="right:50px;" onclick="window.scrollTo({top:0, left:0, behavior:'smooth'});">
            <i class="fas fa-arrow-up"></i>
        </button>

        
    </div>
</template>   
<script> 
    import Vue from 'vue'
    import constants from '../../lib/constants'
    import LoginModal from '../LoginModal.vue'
    import Login from '../../page/user/Login'
    import Vuex from 'vuex'
    import Profile from './Profile.vue'
    import HeaderDetailWriter from './HeaderDetailWriter.vue'
    import HeaderDetailReader from './HeaderDetailReader.vue'
    import SideLogin from './SideLogin.vue'
    import AdminMenu from './AdminMenu.vue'

    import EventBus from '../../eventbus/eventbus.vue'
   
    export default {
        name: 'Header',
        components: { 
            LoginModal,
            Login,
            Profile,
            HeaderDetailWriter,
            HeaderDetailReader,
            SideLogin,
            AdminMenu
        },
        props: ['isHeader'],
        computed:{
        },
        watch: {
            '$route' () {
                if (this.$route.name=='detail' || this.$route.name == 'noticedetail') {
                    this.nowUrl = 'writer'
                } else if (this.$route.name=='write') {
                    this.nowUrl = 'postWrite';
                } else {
                    this.nowUrl = false;
                }
            },
            dark(newDark) {
                localStorage.dark = newDark;
                // //console.log('로컬' + localStorage.dark)
            }
        },
        created() {
            this.$store.commit('isLogin')
            if(this.$store.state.uid == "" ){
                this.login = false;
            } else{
                this.login = true;
            }
        },
        mounted() {
       
            if (this.darkTheme == 'true') {
                // localStorage에 'toString'으로 저장되어 있음
                // 따라서 boolean이 아니라 문자열 'true' 혹은 'false'로 인식하며,
                // if (this.darkTheme) 와 같이 조건문을 걸 경우 존재하기 때문에 무조건 true
                // if (this.darkTheme == true)와 같이 boolean으로 조건문을 걸면 무조건 false
                this.$vuetify.theme.dark = true;
            } else {
                this.$vuetify.theme.dark = false;
            }
            EventBus.$once('modalClose', this.goLogin);
        },
        methods : {
            goLogin() {
                this.showModal = !this.showModal;
            },
            closeDrawer(){
                this.drawer = !this.drawer;
            },
            changeTheme(){
                // //console.log("changeTheme");
                // //console.log(this.darkTheme);
                // //console.log(this.$vuetify);
                // //console.log(this.$vuetify.theme);
                // //console.log(this.$vuetify.theme.dark);
                // //console.log(!this.$vuetify.theme.dark);
                if(this.darkTheme == 'true'){
                    this.$vuetify.theme.dark = false;
                    this.darkTheme = 'false';
                } else if (this.darkTheme == 'false'){
                    this.$vuetify.theme.dark = true;
                     this.darkTheme = 'true';
                }
                localStorage.setItem("darkTheme", this.$vuetify.theme.dark.toString());
            },
            goMain(){
                // //console.log("메인화면으로 이동합니다.");
                if(this.$route.path!=='/') 
                    this.$router.push('/');
            },
            goSearch(){
                this.$router.push('/post/search')
            },
            tempInsert(){
                EventBus.$emit('insertTemp');
            },
            postInsert(){
                EventBus.$emit('insertPost');
            },
        },
        data() {
            return {
                constants,
                keyword : "",
                showModal:false,
                login:true,
                nowUrl: false,
                drawer : false,
                darkTheme : localStorage.getItem("darkTheme"),
                title: "",
                writer:"", 
                content:null,
                taginput: "", 
                tags: [],
                sendtags : '',
                tempsize : 0
           }
        }
    }
    
</script>

<style scoped>
.button{
    position:fixed;
    border-radius:5px;
    background-color:lightgray;
    transition:all .5s ease-in-out;
    padding:5px 10px;
    bottom:50px;
    color:black;
}
.button:hover{
    background-color: rgb(152, 82, 218);
}
</style>