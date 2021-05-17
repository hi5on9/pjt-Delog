<template>
  <div class="manage">
    <p class="titleDoHyeon">공지사항</p>
    <div v-if="lists.length == 0" style="height:430px;">
        표시할 공지사항이 없습니다.
    </div>
    <div class="notice_list"  v-else>
        <v-list class= "nlist" two-line>
            <div @click="goDetail(main.nid)" type="button">
            <span class="mainnotice h6">
                &#x1F4E2; {{main.title}} 
            </span>
            <span class="ndate mainnotice nanumGothic" >{{main.create_date | formatDate }}</span>    
            </div>
        </v-list>
        <v-list class="nlist" two-line v-for="(list,index) in calData()" :key="index">
            <div @click="goDetail(list.nid)" type="button">
                <span class="nanumGothic">
                    {{list.title}} 
                </span>
                <span class="ndate nanumGothic">{{list.create_date | formatDate }}</span>
            </div>
        </v-list>
    </div>
    <v-pagination 
        class = "pnavi" 
        v-model="curpage" 
        :length="totallength">
    </v-pagination >

    <v-btn v-if="$store.state.uid==1" class="notice_btn" @click="$router.push('/admin/notice/write')" >공지작성</v-btn>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api/index';


export default {
    created (){
    instance
        .get('notice',{
            params:{
                is_hidden : 1
            }
        })
        .then((response)=>{
            this.lists = response.data.object
            this.main = this.lists[0]
            // //console.log(this.main)
            this.totallength = Math.ceil((this.lists.length-1) / this.dataPerPage) 
            // //console.log("response : "+this.lists.length);
        })
    },
    methods :{
        startOffset() {
            // //console.log('startoffset :'+(this.curpage - 1) * this.dataPerPage)
            this.startnum = ((this.curpage - 1) * this.dataPerPage)+1;
        },
        endOffset() {
            // //console.log('endoffset :'+(this.startOffset() + this.dataPerPage))
            this.endnum = (this.startnum + this.dataPerPage);
        },
        calData() {
            // //console.log('caldata')
            this.startOffset()
            this.endOffset()
            return this.lists.slice(this.startnum, this.endnum)
        },
        goDetail(nid){
            this.$router.push('/admin/notice/'+nid)
        },
        
    },
    data (){
        return {
            main : [],
            lists : [],
            curpage:1,
            dataPerPage : 9,
            totallength : 1,
            startnum : 1,
            endnum: 10,
        }
    }
}
</script>

<style scoped>

 .manage {
    position: block;
    margin: 0 auto;
    margin-top:100px;
    width:50%;
 }
 .notice_btn{
     float:right;
 }
 .notice_list{
     margin: 60px auto;
     min-height:600px;
 }
.nlist {
    border-bottom: 1px solid rgba(0,0,0,.05);
    height:60px;
}
.ndate{
        float:right;
}
.mainnotice{
    font-weight: bold;
}
</style>