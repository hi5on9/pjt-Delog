<template>
  <div class="feed">
      <div class="feed_header">
         <p class="font-weight-regular titleDoHyeon feedheader_top" >FEED</p>
         <span class="feedheader_des">내가 구독하는글 입니다 : )</span>
         <span class="feedheader_follow"> 
             <span> 구독중 </span> 
             {{$store.state.myfollowings}}
        </span>
      </div>
      <div class="feed_body" v-if="lists.length == 0"> 
         <div class="emptymsg"> 
           <p class="msg1" v-if="$store.state.myfollowings==0">구독 중인 디로거가 없습니다.</p>
           <p class="msg1" v-if="$store.state.myfollowings!=0">더 많은 디로거를 만나보세요</p>
           <p class="msg2">
            홈에서 좋은 글을 만나고 <br>
            마음에 드는 디로거를 구독하세요
           </p>
           <div class="btn btnhome" @click="goHome"><span>홈으로</span></div>
         </div>
      </div>
      <div class="feed_body" v-if="lists.length !=0">
          <div class="feed_article" v-for="list in lists" :key="list.post.pid">
                <div class="article_info" @click="goBlog(list.user.uid)" style="cursor:pointer;">
                    <div class="profilediv">
                        <img class="profileimg" :src="list.user.profile" style=" border:2px solid black" />
                    </div>
                    <span>
                        by {{list.user.nickname}}
                    </span>
                </div>
                <div class="article_contents" @click="goDetail(list.post.pid)" style="cursor:pointer;" >
                    <div class="title">
                    <span>
                       {{list.post.title}} 
                    </span>
                    </div>
                    <div class="content">
                    <span>
                       {{list.post.contents.substring(0,30)}}
                        ...
                    </span>
                    </div>
                    <div class="ect">
                    <span class="date">
                        {{ list.post.create_date | moment("from", "now") }}
                    </span>
                    <span class="hit">
                         <i class="far fa-eye"></i> 25
                    </span>
                    </div>
                </div>
                <div class="article_img">
                    <!-- <img src="@/assets/img/card1.jpg" v-on:click="goDetail(list.post.pid)"> -->
                </div>
          </div>
      </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import vueMoment from 'vue-moment'
import {instance, instanceWithAuth} from '@/api/index'
Vue.use(vueMoment)

export default {
        computed :{
            getTag() {
                
            }
        },
        created () {
            this.getFeed()
        },
        methods: {
            goBlog(uid) {
                this.$router.push('/blog/'+uid);
            },
            goHome(){
                this.$router.push('./')
            },
            goDetail(pid){
                this.$router.push('/post/detail/'+pid)
            },
         getFeed(){
            instanceWithAuth
            .get('main/auth/list/recent')
        .then((response) => {
            //console.log("---getFeed----")
            this.lists = response.data.object
            //console.log(this.lists)
        })
        .catch((response) => {
            // //console.log(response)
            alert("잠시 후 다시 시도해주세요");
        });
    },
},
data : () =>{
    return {
        owner: "",
        lists : [],
        tags : '',
        taglist : [],
        url: `${process.env.VUE_APP_API_URL}`,
    }
}
    
}

</script>




<style scoped>
.feed{
    margin-top:100px;
}
.feed_header{
    border-bottom:3px solid #7e7d7d;
    width: 60%;
    margin:0 auto;
    padding-bottom:30px;

}
.feedheader_top{
    margin-bottom:0px
}
.feedheader_des{
    font-size:18px;
  
}
.feedheader_follow{
    color : #9b9b9b;
    float:right;
    font-size: 24px;
    font-weight: 600;
}
.feedheader_follow span{
    font-size:14px;
}
/* body */
.feed_article{
    height:200px;
    border-bottom:1px solid lightgrey;
    margin:0 auto;
    width:60%;
    padding-top:25px;
    padding-bottom:25px;
}
/* body - profile */
.article_info{
    width:20%;
    height:100%;
    float:left;
    text-align: center;
}
.article_info span{
    margin: 0 auto;
    font-size:12px;
    color:#7e7d7d;
}
/* body - content */
.article_contents{
    width:80%;
    height:100%;
    float:right;
    line-height: 3;
    padding:14px;
    position: relative;
}
.article_contents .title{
    font-size:35px;
    font-weight: bold;
}
.article_contents .ect{
    font-size:13px;
    margin-top:20px;
    color:#7e7d7d;
}

.emptymsg{
    text-align: center;
    margin: 0 auto;
    height: 450px;
    padding:140px;
}
.msg1{
    font-size:18px;
}
.msg2{
    color:#7e7d7d;
    font-size:13px;
}
.btnhome{
    margin-top:10px;
}
.btnhome:hover, .btnhome:focus { animation-duration: 3s; animation-name: rainbowLink; animation-iteration-count: infinite; } 
@keyframes rainbowLink {     
 0% { color: #ff2a2a; }
 15% { color: #ff7a2a; }
 30% { color: #ffc52a; }
 45% { color: #43ff2a; }
 60% { color: #2a89ff; }
 75% { color: #202082; }
 90% { color: #6b2aff; } 
 100% { color: #e82aff; }
}

.profileimg {

        height: 90px;
        width: 90px;
        border-radius: 50%;
        background-color: blueviolet;
        margin : 16px auto;
    }
</style>