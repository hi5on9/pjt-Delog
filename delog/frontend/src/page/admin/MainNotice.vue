<template>
  <div class="MainNoticelist">
    <span class="MainNotice">
        <span  id="noticeicon">&#x1F4E2;</span>
        <span v-if="this.lists.length != 0 "  @click="goDetail(lists[num].nid)"> 
              {{ titles[this.num]}}
        </span>
        <span v-else>
                공지사항이 없습니다!
        </span>
   
    </span>
  </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api/index';
export default {
    data () {
        return {
            lists : [],
            titles: [],
            num : 0,
            intervalid : '',
        }
    },
    computed : {
        changes : {
            get(){
                // //console.log("changes-get:"+this.num)
                return this.num;
            },
            set(v){
                // //console.log("changes-set:"+v)
                this.num=  v;
            }
        }
    },
    mounted (){
        this.changeNotice()
    },
    created () {
        instance
        .get('notice/main')
        .then((response)=>{
            this.lists = response.data.object
            //console.log("notice/main lists : ");
            //console.log(this.lists);
            for(var i=0;i<this.lists.length;i++){
                this.titles.push( this.lists[i].title)
            }
            // //console.log(this.titles)
        })
    },
    methods : {
        changeNotice() {
            this.intervalid = setInterval(()=>{
                this.num = (this.num+1)% this.titles.length;
                // //console.log("공지번호:"+this.num);
            },10000);
        },
        goDetail(nid){
            this.$router.push('admin/notice/'+nid)
        },
    }
}
</script>

<style scoped>
.MainNoticelist {
    width: 60%;
    margin : 40px auto;
}
.MainNotice{
    display:block;
    margin: 0 auto;
    width:100%;
    color:gray;
    font-weight: bold;
    cursor: pointer;
}
#noticeicon{
    color:#ff5f40;
    margin-right:10px;
}
</style>