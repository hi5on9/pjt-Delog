<template>
    <div class="outCenter">
        <div class="like_header">
            <p class="font-weight-regular titleDoHyeon likeheader_top">
                <i class="fas fa-bookmark" style="margin-right:10px;"></i>스크랩 목록</p>
             <span class="likeheader_des"></span>
        </div> 
         <div class="feed_body">
          <div class="feed_article" v-for="list in lists" :key="list.post.pid">
                <div class="article_info">
                    <div class="profilediv">
                        <img class="profileimg" :src="list.user.profile" style=" border:2px solid black" />
                    </div>
                    <span @click="gowriterblog(list.user.uid)" style="cursor:pointer;">
                        by {{list.user.nickname}}
                    </span>
                </div>
                <div class="article_contents">
                    <div class="title" @click="goDetail(list.post.pid)">
                    <span>
                       {{list.post.title}} 
                    </span>
                    </div>
                    <div class="content" @click="goDetail(list.post.pid)">
                    <span>
                       {{list.post.contents.substring(0,30)}}
                        ...
                    </span>
                    </div>
                    <div class="ect">
                    <span class="date">
                        {{ list.post.create_date | formatDate}}
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
        <infinite-loading  @infinite="infiniteHandler" spinner="circles"></infinite-loading>  
    </div>
</template>

<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import { instanceWithAuth } from '@/api';
    import InfiniteLoading from 'vue-infinite-loading'

export default {
    components : {
            InfiniteLoading
    },
    created (){
        // //console.log("---좋아요한 페이지 --")
    },
    methods :{
        infiniteHandler($state){
            instanceWithAuth
            .get('post/likelist/',{
                params:{
                    uid : this.$store.state.uid,
                    page : this.page
                }
            }).then(({data})=>{
                    setTimeout (() =>{
                        if(data.object.length){
                            this.page +=1;
                            this.lists = this.lists.concat(data.object)
                            
                            // console.log(this.lists)
                            $state.loaded()
                        
                        }else {
                            $state.complete()
                        }
                    },1000)
                })
        },
        goDetail(pid){
            this.$router.push('/post/detail/'+pid)
        },
        gowriterblog(uid) {
            this.$router.push('/blog/'+uid);
        },
    },
    data() {
        return{
            lists : [],
            page: 0,
             url: `${process.env.VUE_APP_API_URL}`,
        } 
    }

}
</script>

<style scoped>
.container {
    position : relative;
}
.like_header{
    border-bottom:3px solid #7e7d7d;
    padding-bottom:30px;
}
.likeheader_top{
    margin-bottom: 0px;
}
.likeheader_des{
    font-size:18px;
}
.blog_card{
    margin :10px;
    position: relative;
    display: inline-block;
    width:100%;
}
.feed_body{
    min-height: 450px;
}
.feed_article{
    height:200px;
    border-bottom:1px solid lightgrey;
    margin:0 auto;
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
    cursor: pointer;
}
.article_contents .title{
    font-size:35px;
    font-weight: bold;
}
.article_contents .content{
    cursor: pointer;
}
.article_contents .ect{
    font-size:13px;
    margin-top:20px;
    color:#7e7d7d;
}
.profileimg {

        height: 90px;
        width: 90px;
        border-radius: 50%;
        background-color: blueviolet;
        margin : 16px auto;
    }
</style>