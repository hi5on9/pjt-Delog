<template>
  <div class="manage outCenter">
    <p class="titleDoHyeon">신고 내역</p>
    <div v-if="lists.length == 0" style="height:400px;"> 
        표시할 신고 내역이 없습니다.
    </div>
    <div class="notice_list"  v-else>
        <v-list class="nlist" two-line v-for="(list,index) in calData()" :key="index">
            <span class="nanumGothic">
                {{list.reason}} 
            </span>
            <span class="ndate nanumGothic">{{list.create_date | formatDate }}</span>
        </v-list>
    </div>
    <v-pagination 
        class = "pnavi" 
        v-model="curpage" 
        :length="totallength">
    </v-pagination >
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api/index';


export default {
    created (){
        this.getReport();
    },
    methods :{
        getReport() {
            instanceWithAuth
            .get('report',{
                params:{
                    uid : this.$store.state.uid,
                }
            })
            .then((response)=>{
                this.lists = response.data.object;
                this.totallength = Math.ceil((this.lists.length-1) / this.dataPerPage);
                // //console.log(this.lists);
            })
        },
        startOffset() {
            // //console.log('startoffset :'+(this.curpage - 1) * this.dataPerPage)
            this.startnum = ((this.curpage - 1) * this.dataPerPage);
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
        // goDetail(nid){
        //     this.$router.push('../../admin/notice/'+nid)
        // },
    },
    data (){
        return {
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