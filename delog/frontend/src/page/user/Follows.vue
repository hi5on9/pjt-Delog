<template>
<div>
    <topfollow-user style="border-bottom: 1px solid;"/>
    <div id="tabs" class="container" style=" margin:100px auto;">
        <div class="tabs">
            <a v-on:click="tabChange(2)" v-bind:class="[ activetab === 2 ? 'active' : '' ]" style="font-family: 'Do Hyeon', sans-serif; font-size: 1.25rem;">팔로워</a>
            <a v-on:click="tabChange(1)" v-bind:class="[ activetab === 1 ? 'active' : '' ]" style="font-family: 'Do Hyeon', sans-serif; font-size: 1.25rem;">팔로잉</a>

        </div>
        <div class="content">
            <div class="tabcontent">
                <ul>
                    <v-list :key="list.uid" v-for="list in lists" class="followlist">
                        <span class ="nick" v-on:click="goProfile(list.uid)" >
                                {{ list.nickname }} 
                        </span>
                        <span class="profilediv">
                            <img class="profileimg" :src="list.profile" style=" border:2px solid black" />
                        </span>
                        <!-- <i class="far fa-user fa-2x " type="button" style="width:10%; line-height:60px;"></i> -->
                        <span class="btn insertbtn" v-if="isMine" @click="unfollow(list.uid)" style="float:right; margin-top:8px;">
                            <span v-if="activetab == 1">언팔로우</span>
                            <span v-if="activetab == 2">삭제</span>
                        </span>
                    </v-list> 
                </ul>
            </div>
        </div>
    </div>
</div>
</template>

<script>
    import axios from "axios"
    import vue from 'vue'
    import vuex from 'vuex'
    import constants from '@/lib/constants'
    import {instance, instanceWithAuth} from '@/api/index';
    import TopfollowUser from '@/page/post/main/TopfollowUser.vue'
    export default {
        components:{
            TopfollowUser,
        },
        created () {
            // ** store에 type 생성해야함 !! **  store 에서 type 받아와서 먼저 받은거 부터 tab 될 수 있게 없으면 디폴트는
            // 1 !!
            this.activetab = this.$store.state.ftype
            this.getFollows()
            if(this.$route.params.uid == this.$store.state.uid) this.isMine = true;
            
        },
        methods: {
            tabChange(tabNum) {
                // //console.log(tabNum)
                this.activetab = tabNum;
                this.getFollows()
            },
            // 해당 유저의 팔로잉 목록 들고 오기!!
            getFollows() {
                // //console.log("uid:"+this.$route.params.uid +","+ this.activetab)
                instance
                    .get("user/followlist", {
                        params: {
                            uid: this.$route.params.uid,
                            type : this.activetab
                        }
                    })
                    .then((response) => {
                        // //console.log(response);
                        this.lists = response.data.object;
                    })
                    .catch((response) => {
                        alert("목록을 불러올 수 없어요!!");
                    });
            },
            unfollow(uid){
            //   //console.log("---언팔로우하기---setunfollow()")
              if(this.activetab ==2){
                  //follower = 남 following 나
                  this.uid1 = uid
                  this.uid2 = this.$store.state.uid
              }else {
                  //follower 나 following 너
                  this.uid1 = this.$store.state.uid
                  this.uid2 = uid
              }
            //   //console.log(this.uid1+","+this.uid2);
             instanceWithAuth
                .delete('user/unfollow',{
                    params :{
                        f_uid : this.uid1,
                        s_uid : this.uid2
                    }
                 })
                .then((response) => {
                    // //console.log(uid1+'이'+uid2+'언팔로우 했음!')
                    // //console.log(response)
                    })
                .catch((response) => {
                    // alert('언팔로우 불가!')
                    // 고쳐야함 ...
                    location.reload()
            });                  
            },
            goProfile(uid){
               this.$store.commit('getowner',uid)
                this.$router.push('/blog/'+uid);
            }
     
        },
        data: () => {
            return {lists: [],activetab: 2, value: "", uid1:'',uid2:'',isMine : false, myfollowstate:false,
            url: `${process.env.VUE_APP_API_URL}`,}
        }
    }
</script>

<style scoped="scoped">
    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }
    .profileimg {
        height: 50px;
        width: 50px;
        border-radius: 150px;
        background-color: blueviolet;
        margin-top:10px;
        margin-bottom:10px;
        margin-right : 20px;
        float : left;
    }
    .container {
        width: 50%;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 0.9em;

    }

    .nick {
        cursor:pointer;
        height : 100%;
        font-size : 2em;
        vertical-align: middle;
        
    }

    /* Style the tabs */
    .tabs {
        overflow: hidden;
        margin-bottom: -2px;
    }

    .tabs ul {
        list-style-type: none;
        margin-left: 20px;
    }

    .tabs a {
        width: 50%;
        float: left;
        cursor: pointer;
        padding: 12px 24px;
        transition: background-color 0.2s;
        border: 1px solid #ccc;
        border-right: none;
        background-color: darkgray;
        border-radius: 10px 10px 0 0;
        font-weight: bold;
        color: #e1e1e1;
    }
    .tabs a:last-child {
        border-right: 1px solid #aaa;
    }

    /* Change background color of tabs on hover */
    .tabs a:hover {
        background-color: #aaa;
        color: #fff;
    }
    /* Styling for active tab */
    .tabs a.active {
        background-color: #fff;
        color: #484848;
        border-bottom: 2px solid #fff;
        cursor: default;
    }

    /* Style the tab content */
    .tabcontent {
        min-height: 700px;
        padding: 30px;
        border: 1px solid #ccc;
        border-radius: 10px;
    }

    .followsnick{
        width:60%;
        border:1px solid red;
    }

    .followlist{
        height:80px;
    }
</style>