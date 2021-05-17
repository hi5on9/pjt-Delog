<template>
    <div>
        댓글 : {{reply.length}}
        <div style="margin-top:10px;">
            <div v-if="this.$store.state.nickname" style=" border:1px solid #bac5d159; background-color:white;border-radius:20px;">
                <div style="margin-left:3px; height:60px; line-height:60px;">
                    <img class="profileimg" :src="tmpProfile" style=" border:2px solid #bac5d159; width:40px; height:40px; margin-right:5px;" />
                    <span class="h6" style="color:gray;"><b>{{this.userName}}</b>님</span>
                </div>
                <textarea id="commentbox" rows="4" cols="70" style="margin-top: 10px; margin:0 2%; background-color:white;  resize:none; width:95%; height:100%;" placeholder="댓글을 입력하세요." v-model = "contents" />
                <div style="margin-top:0px; margin-left:80%; text-align:right;">
                    <div class="btn insertbtn" @click="writeReply(0)" style="width:70px; height:30px; line-height:30px;">
                        <span>등 록</span>
                    </div>
                </div>
            </div>
            <div v-else style=" padding:10px; background-color:#bac5d159">
                <div v-on:click="goLogin" style="background-color:white; color:#bac5d159; width:100%; height:70px; border-radius:7px;cursor:pointer;" placeholder="댓글을 입력하세요.">
                    <p style="padding-left: 10px;">로그인이 필요한 기능입니다</p>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="tabs" v-if="reply.length != 0">
            <span class="commentSort" v-on:click="getReply(1)" v-bind:class="[ activetab === 2 ? 'active' : '' ]">
                <span>좋아요순</span>
            </span>
            <span class="commentSort" v-on:click="getReply(2)" v-bind:class="[ activetab === 3 ? 'active' : '' ]">
                <span>최신순</span>
            </span>
            <span class="commentSort" v-on:click="getReply(3)" v-bind:class="[ activetab === 4 ? 'active' : '' ]">
                <span>호감순</span>
            </span>
            <br><br>
        </div>
        <div class="content">
            <div class="tabcontent">
                <div v-bind:key = "list.reply.rid" v-for="(list, index) in reply" style="border-bottom:1px solid #bac5d159; margin-top:10px;">
                    <span>
                        <span class="profilediv">
                            <img class="profileimg" :src="list.user.profile"/>
                        </span>

                        <span class="h6 font-weight-regular" @click="gowriterblog(list.user.uid)" type="button" style="padding-left: 2px; padding-right: 2px;">
                            {{ list.user.nickname }}
                        </span>
                        <span v-if="uidTmp == list.user.uid" style="float:right;">
                            <i class="fas fa-times" @click="deleteReply(list.reply.rid, 0)" style="cursor:pointer"></i>
                        </span>
                        <br>
                        <span class="text-caption">{{ list.reply.create_date | formatDate }} </span>
                        <br>
                    </span>
                    <p class="subtitle" style="padding-top:10px;">
                        {{list.reply.contents}}
                        <br>
                    </p> 
                    <span style = "float : right">
                        <span>
                        <span @click="checkLike(list.reply.rid, list.reply.prid)" type="button" style="margin-right:5px;">
                            <i class="far fa-thumbs-up"></i>
                            {{list.reply.likes}}
                        </span>
                        <span @click="checkHate(list.reply.rid, list.reply.prid)" type="button">
                            <i class="far fa-thumbs-down"></i>
                            {{list.reply.hates}}
                        </span>
                        </span>
                    </span>

                    <div class="btn insertbtn" @click="setToggleTemp(list.reply.rid)" style="width:50px; height: 30px; font-size:0.7rem; line-height:30px; margin-left:0px;">
                        답 글 {{rereplylist[index]}}
                    </div>
                    <br>
                    <div v-if="isStatusOn && (toggleTemp == list.reply.rid)" style="margin-bottom:50px">
                        <div >
                            <div v-bind:key = "list.reply.rid" v-for="list in rereply"  style=" margin-top:10px;">  
                                <div style="height:20px; width:20px; float:left; margin-left:10px; border-left:1px solid #bac5d159; border-bottom:1px solid #bac5d159;"></div>
                                <div style="margin-left:50px; border-bottom:1px solid #bac5d159;">
                                <span>
                                    <span class="profilediv">
                                        <img class="profileimg" :src="list.user.profile"/>
                                    </span>
                                    <span class="h6 font-weight-regular" @click="gowriterblog(list.user.uid)" type="button" style="padding-left: 2px; padding-right: 2px;">
                                        {{ list.user.nickname }}
                                    </span>
                                    <span v-if="uidTmp == list.user.uid" style="float:right;">
                                        <i class="fas fa-times" @click="deleteReply(list.reply.rid, 1, list.reply.prid)" style="cursor:pointer"></i>
                                    </span>
                                    <br>
                                    <span class="text-caption">{{list.reply.create_date | formatDate}}</span>
                                </span>
                                <br>
                                <p class="subtitle" style = "float : center; padding-top:10px;">
                                    {{list.reply.contents}}
                                </p>
                                <span style = "float : right">
                                    <span>
                                        <span @click="checkLike(list.reply.rid, list.reply.prid)" type="button" style="margin-right:5px;">
                                            <i class="far fa-thumbs-up"></i>
                                            {{list.reply.likes}}
                                        </span>
                                        <span @click="checkHate(list.reply.rid, list.reply.prid)" type="button">
                                            <i class="far fa-thumbs-down"></i>
                                            {{list.reply.hates}}
                                        </span>
                                    </span>
                                </span>
                                <br>
                                </div>
                            </div>
                            <br>
                            <div style=" padding:10px; border-radius:10px;border:1px solid #bac5d159; background-color:white;" class="commentinputbox">
                                <textarea rows="3" style="background-color:white; width:100%; padding-left:10px;" placeholder="댓글을 입력하세요." v-model = "contents2"/>
                                <div style="margin-top:10px; margin-bottom:0px; margin-left:80%; text-align:right;">
                                    <div class="btn insertbtn" @click="writeRereply(list.reply.rid)" style="width:70px; height:30px; line-height:30px; margin-top:0px; margin-bottom:5px;">
                                        <span>등 록</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
        </div>
        <LoginModal v-if="showModal" @close="showModal = false">
            <h3 slot="header">
                
                <span class="doHyeon" style="font-size:1.5rem; color:#bac5d159;">로그인</span>
                <i class="closeModalBtn fas fa-times" @click="showModal = false" style="float:right; color:#bac5d159; cursor:pointer"></i>
                <br><br>
                <Login v-on:closeModal="goLogin" ></Login>
            </h3>

            <div slot="body">
            </div>
        </LoginModal>
    </div>
</template>
<script>
import {instanceWithAuth, instance} from '@/api/index'
import Vuex from 'vuex'
import LoginModal from '../../components/LoginModal.vue'
import Login from '../user/Login'

export default {
    components:{
        LoginModal,
        Login,
    },
    data(){
        return {
            showModal:false,
            uidTmp : 0,
            uid : 0,
            pid : 0,
            type: 1,
            rid : 1,
            reply : [],
            rereply : [],
            rereplylist : [],
            contents : "",
            contents2 : "",
            activetab : 1,
            isStatusOn: false,
            url: `${process.env.VUE_APP_API_URL}`,
            userName: this.$store.state.nickname,
            tmpProfile:'',

        }
    },
    created(){
        this.uidTmp = this.$store.state.uid,
        this.getReply(1)
        this.getDetail()
        
    },
    computed:{
        param:function(){
            return this.$route.params.id;
        },
        requestUrl(){
            return `${process.env.VUE_APP_API_URL}` + list.user.profile;
        }
    },
    methods:{
            getDetail() {
            instanceWithAuth
                .get('user/account/userinfo', {})
                .then((response) => {
                    this.tmpProfile = response.data.object.profile;
                })
                .catch((error) => {
                    alert('회원정보 조회 실패' + error);
                });
            },
        gowriterblog(tempuid) {
            this.$router.push('/blog/'+tempuid);
        },
        setToggleTemp(temprid){
            this.toggleTemp = temprid
            this.toggleOnOff(this.toggleTemp);
            this.isStatusOn = !this.isStatusOn;
        },
        async toggleOnOff(temprid) {

            await instance
            .get('post/reply/re',{
                params : {
                    pid : this.$route.params.id,
                    prid : temprid,
                    type : 1
                }
            })
            .then((response) => {
                this.rereply = response.data.object
            })
            .catch((error) => {
                alert("toggleOnOff에러")
            })
            ;
        },
        getReply(temp){
            instance
            .get('post/reply',{
                params : {
                    pid : this.$route.params.id,
                    type : temp
                }
            })
            .then((response)=>{
                this.reply = response.data.object
                this.getrereply()
            })
            .catch((error) => {
                // alert("getReply에러")
                // 댓글이 없을 시 에러 경고창 발생
                // 주석 처리함
            })
        },
        writeReply(tempprid){
            instanceWithAuth
            .post("post/reply", {
                uid : this.$store.state.uid,
                pid : this.$route.params.id,
                prid : tempprid,
                contents: this.contents
            })
            .then( (response)=>{
                if(tempprid == 0){
                    this.getReply(1);
                } else {
                    this.toggleOnOff(tempprid);
                }
                alert("저장 완료");
                this.contents="";
            })
            .catch((response)=>{
                alert("로그인이 필요한 기능입니다.(댓글 쓰기)");
            })
        },
        writeRereply(tempprid){
            instanceWithAuth
            .post("post/reply", {
                uid : this.$store.state.uid,
                pid : this.$route.params.id,
                prid : tempprid,
                contents: this.contents2
            })
            .then( (response)=>{
                if(tempprid == 0){
                    this.getReply(1);
                } else {
                    this.toggleOnOff(tempprid);
                }
                alert("저장 완료");
                this.contents2="";
            })
            .catch((response)=>{
                alert("로그인이 필요한 기능입니다.(댓글 쓰기)");
            })
        }, 
        deleteReply(temprid, type, tempprid){
            instanceWithAuth
            .delete('post/reply',{
                params : {
                    rid : temprid
                }
            }).then((response)=>{
                if(type == 0){
                    this.getReply(1);
                } else {
                    this.toggleOnOff(tempprid);
                }
                alert("삭제되었습니다.")
            }).catch((error)=>{
                alert("로그인이 필요한 기능입니다.(댓글 삭제)");
            })
        },
        checkLike(temprid, tempprid) {
            instanceWithAuth
            .get("post/reply/listcheck", {
                params : {
                uid : this.$store.state.uid,
                rid : temprid,
                type : 1
                }
            })
            .then((response) => {
                if(response.data.object){
                    alert("좋아요 취소!");
                    this.replyLike(temprid, 3, tempprid);
                }
                else if(!response.data.obejct){
                    alert("좋아요!");
                    this.replyLike(temprid ,1, tempprid);
                }
            })
            .catch((response)=> {
                alert("로그인이 필요한 기능입니다.(좋아요 클릭)");
            })
        },
        checkHate(temprid, tempprid) {
            instanceWithAuth
            .get("post/reply/listcheck", {
                params : {
                uid : this.$store.state.uid,
                rid : temprid,
                type : 2
                }
            })
            .then((response) => {
                if(response.data.object){
                    alert("싫어요 취소!");
                    this.replyHate(temprid, 4, tempprid);
                }
                else if(!response.data.obejct){
                    alert("싫어요!");
                    this.replyHate(temprid ,2, tempprid);
                }
            })
            .catch((response)=> {
                alert("로그인이 필요한 기능입니다.(싫어요 클릭)");
            })
        },
        replyLike(temprid, temp, tempprid){
            instanceWithAuth
            .post("post/reply/btn", {
                uid : this.$store.state.uid,
                rid : temprid,
                type : temp
            })
            .then( (response)=>{
                if(tempprid == 0){
                    this.getReply(1);
                } else {
                    this.toggleOnOff(tempprid);
                }
            })
            .catch((response)=>{
            })
        },
        replyHate(temprid, temp, tempprid){
            instanceWithAuth
            .post("post/reply/btn", {
                uid : this.$store.state.uid,
                rid : temprid,
                type : temp
            })
            .then( (response)=>{
                if(tempprid == 0){
                    this.getReply(1);
                } else {
                    this.toggleOnOff(tempprid);
                }
            })
            .catch((response)=>{
            })
        },
         async getrereply() {
            // //console.log("안녕");
            for(let i = 0; i < this.reply.length ; i ++) {
                // //console.log(this.reply[i].reply.rid);
                 var result = await this.toggleOnOff(this.reply[i].reply.rid);
                this.rereplylist[i] = this.rereply.length;
                // //console.log("길이 -> " + this.rereplylist[i]);
            }
            // //console.log("done");
        },
        goLogin() {
                this.showModal = !this.showModal;
        },
    }
};
</script>
<style scoped>
a{
    margin-right : 13px;
}

 .profileimg {
        height: 50px;
        width: 50px;
        border-radius: 150px;
        background-color: blueviolet;
        margin-top:5px;
        margin-bottom:10px;
        margin-right : 20px;
        float : left;
    }
    .commentSort {
        margin: 0 7px;
        width:50px; 
        height:30px; 
        font-size:0.75rem; 
        line-height:32px; 
        cursor:pointer;
    }
    .commentSort:hover{
        text-decoration:none;
        border-bottom:1.5px solid blueviolet;
        padding-bottom:3px;
    }
</style>
