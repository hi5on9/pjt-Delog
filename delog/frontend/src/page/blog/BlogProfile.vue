<template>
    <div class="profilebox">
        <div class="profilediv">
            <img class="profileimg" :src="profile" style=" border:2px solid black" />
            <!-- <h3>{{list.nickname}}</h3> -->
        </div>
        <div>
            <div class="userinfo" >
                <div>
                    <p class="blognickname">{{ list.nickname }}</p>
                </div>
                <div class="notmyblog" v-if="!isMine" style="float:right;">
                    <span >
                        <span v-if="$store.state.isfollow== false" @click="setfollow" class="profilebtn" style="border: 1px solid #00bdaa; color:#00bdaa">
                            <i class="fas fa-heart"></i> 팔로우
                        </span> 
                        <span v-if="$store.state.isfollow== true" @click="setunfollow" class="profilebtn">
                            <i class="fas fa-heart-broken"></i>언팔로우
                        </span>
                    </span>
                    <span >
                        <span @click="goMessage" class="profilebtn">
                                <i class="fas fa-envelope"></i>  쪽지
                        </span>
                        <v-menu offset-y left>
                            <template v-slot:activator="{ on, attrs }">
                                <i class="fas fa-ellipsis-v" v-bind="attrs" v-on="on" style="padding:0 13px;"></i>
                            </template>
                            <v-list style="min-width:100px; text-align:center;">
                                <button @click="goReport" class="nanumGothic">
                                    신고하기
                                </button>
                            </v-list>
                        </v-menu>
                    </span>
                </div>
                <div v-if="isMine" style="float:right;">
                    <span @click="$router.push('/blogsetting')" class="profilebtn">
                        <i class="fas fa-cog"/>
                        블로그 관리
                    </span>               
                </div>
                <div class="fbtn">
                    <span class="nanumGothic" @click="gofollowpage(1)" style="cursor:pointer;">
                        팔로잉 <b class="b"> {{ countfollowings }} </b>
                    </span>
                    <span class="nanumGothic" @click="gofollowpage(2)" style="margin-left:20px; cursor:pointer;">
                        팔로워 <b class="b"> {{ countfollowers }} </b>
                    </span>
                </div>
            </div>
        </div>


        <!--쪽지 Modal -->
        <Modal v-if="MsgModal" @close="MsgModal = false" style="padding-top:150px; padding-bottom:100vh; color:black;">
            <div slot="header" style="padding-left:30px; height:90px;">
                <div >
                    <img class="profileimg" :src="profile" style="width:60px;height:60px; border:2px solid black;float:left; " />
                <p style=" float:right; width:250px; line-height:70px;"><b>{{list.nickname}}</b>님에게 쪽지를 보냅니다.</p>
                </div>
            </div>
            <div slot="body" >
                <send-note/>
            </div>
        </Modal>

        <!-- 신고하기 Modal  -->
        <Modal v-if="reportModal" @close="reportModal = false" style="padding-top:150px; padding-bottom:100vh; color:black;">
            <div slot="header" style="padding-left:30px; height:90px;">
                <div >
                <p style="line-height:70px; font-size:20px;"><b>신고 사유를 선택해 주세요</b></p>
                </div>
            </div>
            <div slot="body" >
                <send-report/>
            </div>
        </Modal>
    </div>
</template>

<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import {instance, instanceWithAuth} from '@/api/index'
    import Modal from '@/components/Modal';
    import SendNote from '@/page/note/SendNote.vue'
    import SendReport from '@/page/report/SendReport.vue'
    
    export default {
        components : {
            Modal,
            SendNote,
            SendReport
        },
        created (){
        this.$store.commit('idCheck',this.$route.params.uid)
        this.getBlog()
        this.getFollowers()
        if(this.$store.state.login_state == 'SAME') this.isMine = true;
        if(!this.isMine){
            this.followcheck()
            // //console.log("남 블로그 팔로우 상태:"+this.$store.state.isfollow)
        }
       
      },
      watch : {
        '$route' (to, from) { 
        this.$store.commit('idCheck',this.$route.params.uid)
        this.getBlog()
        this.getFollowers()
        if(this.$store.state.login_state == 'SAME') this.isMine = true;
        if(!this.isMine){
            this.followcheck()
            // //console.log("남 블로그 팔로우 상태:"+this.$store.state.isfollow)
            }
        }
      },
      computed :{
          countfollowers (){
            //   //console.log('computed - 팔로워');
              return this.$store.state.thisfollowers
          },
          countfollowings () {
            //   //console.log('computed - 팔로잉');
              return this.$store.state.thisfollowings
          }
      },
      methods :{
          goMessage(){
               if(this.$store.state.uid==''){
                alert('로그인이 필요한 기능입니다.')
            }else{
                this.$store.state.recipient = this.$route.params.uid
                // this.$router.push('/sendnote');
                this.MsgModal = !this.MsgModal;
            }
          },
            goReport(){
                if(this.$store.state.uid==''){
                alert('로그인이 필요한 기능입니다.')
            }else{
                this.$store.state.recipient = this.$route.params.uid
                // this.$router.push('/report');
                this.reportModal = !this.reportModal
            }
          },
          followcheck(){
            instanceWithAuth
            .get('user/followcheck',{
                params:{
                    f_uid : this.$store.state.uid,
                    s_uid : this.$route.params.uid,
                    type : 1
                }
            }).then((response)=>{
                // //console.log("[팔로잉체크성공]")
                // //console.log(response.data.object)
                if(response.data.object==true) {
                    this.followstate = true
                    this.$store.state.isfollow = true
                }else{
                     this.followstate  = false
                    this.$store.state.isfollow = false
                }
            }).catch((error)=>{
                // //console.log(error)
            })
          },
          setfollow(){
            if(this.$store.state.uid==''){
                alert('로그인이 필요한 기능입니다.')
            }else{
             instanceWithAuth
                .post('user/follow',{
                    follower_id : this.$store.state.uid,
                    following_id : this.$route.params.uid
                 })
                .then((response) => {
                    // //console.log('팔로우 했음!')
                    this.$store.state.isfollow  = true
                    })
                .catch((response) => {
                    alert('팔로우 불가!')
            });
            this.getFollowers()
            }         
          },
          setunfollow(){
            //   //console.log("---언팔로우하기---setunfollow()")
             instanceWithAuth
                .delete('user/unfollow',{
                    params : {
                        f_uid : this.$store.state.uid,
                        s_uid : this.$route.params.uid
                    }
                 })
                .then((response) => {
                    // //console.log('언팔로우 했음!')
                    this.$store.state.isfollow  = false
                })
                .catch((response) => {
                    alert('언팔로우 불가!')
            });
            this.getFollowers()                
          },
        gofollowpage(type) {
               this.$store.commit('followingtype',type)
                this.$router.push('/user/follows/'+this.$route.params.uid);
            },
          getBlog(){
              instance
                 .get('user/search',{
                      params:{
                         uid : this.$route.params.uid
                     }
                 })
                    .then((response) => {
                       this.list = response.data.object
 
                        this.profile = response.data.object.profile;
       

                    })
                    .catch((response) => {
                        alert(response)
                    });
          },
          getFollowers(){
                instanceWithAuth
                    .get("user/followlist/", {
                        params: {
                            uid: this.$route.params.uid,
                             type : 2
                        }
                    })
                    .then((response) => {
                        this.followers =  response.data.object.length
                        this.$store.state.thisfollowers = this.followers
                        // //console.log("팔로워:"+this.followers)
                    })
                    .catch((response) => {
                        alert("목록을 불러올 수 없어요!!");
                    });
                    instanceWithAuth
                    .get("user/followlist/", {
                        params: {
                            uid: this.$route.params.uid,
                             type : 1
                        }
                    })
                    .then((response) => {
                        this.following =  response.data.object.length
                        this.$store.state.thisfollowings = this.following
                        //  //console.log("팔로잉:"+this.following)
                    })
                    .catch((response) => {
                        alert("목록을 불러올 수 없어요!!");
                    });
          }
      },
      data : () =>{
        return {
            list : [], following : 0,followers:0,isMine : false, followstate : false,
            profile :'',
            url: `${process.env.VUE_APP_API_URL}`,
            MsgModal : false,
            reportModal: false,
            }
        }
    }
</script>

<style scoped>
    .profilebox{
        width: 55%;
        margin:0 auto;
        margin-top:100px;
        padding:30px;
        height: 300px;
    }
    .profilediv {
        float: right;
    }
    .profileimg {
        height: 150px;
        width: 150px;
        border-radius: 150px;
        background-color: blueviolet;
        margin-top:10px;
        margin-bottom:10px;
    }
    .blognickname{
        font-weight: bold;
        font-size:45px;
    }
   
    .fbtn div{
        width:fit-content;
        padding:5px;
        line-height: 2;
        font-size:15px;
        cursor: pointer;
        
    }
    .b{
        font-size:20px;
    }
    .fbtn div:hover .b{
        text-decoration: underline;
    }
    .profilebtn{
        width:120px;
        height:40px;
        padding:5px;
        margin: 0 5px;
        line-height: 40px;
        border-radius: 10px;
        border: 1px solid rgb(133, 133, 133);
        cursor: pointer;
        transition: all 0.4s;
        margin-top:10px;   
        font-size:15px; 
    }
    .profilebtn i{
        margin-right:13px;
    }
    .userinfo{
        margin-top:80px;
    }
    .profilebtn:hover{
        /* background-color: #f5f5f5; */
        color:rgb(134, 134, 134);
    }
</style>