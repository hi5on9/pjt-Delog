<template>
  <div class="templists outCenter">
    <p class="titleDoHyeon">임시저장 목록</p>
    <div class="templist" v-for = "list in lists"  :key="list.post.pid" @click="updateTemplist(list.post.pid)" style="cursor:pointer">
      <div>
        <div id="temptitle">
          <span class="h6" style="margin-bottom:5px;"> {{list.post.title}} </span>
          <b class="font-weight-thin font-italic text-body-2"> at </b>
          <span class="font-weight-thin font-italic text-body-2" style="padding-left: 1px;">
              {{ list.post.create_date | formatDate}}
          </span>
        </div>
        <div class="tempbtn" style="margin-top: 10px;">
          <v-btn @click="delTemplist(list.post.pid)" style="float:right;">삭제</v-btn>
          &emsp;
          <v-btn @click="gopost(list.post.pid)" style="float:right;">등록</v-btn>
        </div>
      </div>
      <hr>
    </div>
  </div>
</template>

<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import {instanceWithAuth, instance} from '@/api/index'
export default {
     created () {
        this.getTemplist()
     },
     methods : {
       //임시저장 리스트
        getTemplist(){
            instanceWithAuth
            .get('post/templist',{
                params:{
                    uid : this.$store.state.uid
                }
            }).then((response)=>{
                // //console.log("---임시저장 게시물 --")
                this.lists = response.data.object
                // //console.log(response)

            }).catch((error)=>{
                alert("잠시 후 다시 시도해주세요")
            })
        },
        //임시수정
        updateTemplist(pid){
           this.$router.push('./update/'+pid)
        },
        //임시삭제
        delTemplist(postid){
          // //console.log("delpost"+postid)
            instanceWithAuth
            .delete('post/temp',{
                params :{
                        pid : postid
                    }
            }).then((response)=>{
                // //console.log("---임시저장 삭제 --")
                alert('임시 저장 삭제')
                // //console.log(response)
                location.reload();

            }).catch((error)=>{
                alert("잠시 후 다시 시도해주세요")
            })
        },
        //게시물로 등록
        gopost(postid){
          // //console.log("gopost"+postid)
              instanceWithAuth
                  .put('post/tempchange', {
                        pid: postid
                    //pid의 임시저장 게시물안의 내용 ...
                    })
                    .then((response)=>{
                       alert("등록 성공");
                       this.$router.push('/');
                    })
                    .catch((response)=>{
                        // //console.log(response)
                        alert("다시 입력해주세요.");
          })
        }
     }, 
    data: () => {
        return {
            lists : [],
        }
    }

}
</script>

<style scoped>
.templist{
  display: grid;
}
#temptitle{
  float:left;
  height:60px;
  line-height: 60px;
}
.tempbtn{
  float:right;
  width:35%;
}


</style>