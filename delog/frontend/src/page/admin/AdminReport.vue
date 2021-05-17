<template>
  <div class="manage outCenter">
    <p class="titleDoHyeon">신고내역</p>
    <div v-if="lists.length == 0"> 
        표시할 신고 내역이 없습니다.
    </div>
    <div class="notice_list"  v-else>
        <v-list class="nlist" two-line v-for="(list,index) in calData()" :key="index">
            <span style = "margin-right : 50px;"> 신고자)  {{list.reporter}}  </span>    
            <span> 대상자)  {{list.reported}} </span>
            
            <div>        {{list.reason}} 

                <span class="ndate nanumGothic">{{list.create_date | formatDate }}</span>

            </div>
            <div style="float:right; margin-top:0px;">
                <div class="btn insertbtn" @click="checkPrison(list)" style="width:50px; margin-top:0px;">
                    <span>등 록</span>
                </div>
                <div class="btn insertbtn" @click="deleteReport(list.rid)" style="width:50px; margin-top:0px;">
                    <span>기 각</span>
                </div>
            </div>    
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
        delteReport(){

        },
        getReport() {
            instanceWithAuth
            .get('report/all',{
            })
            .then((response)=>{
                //console.log(response);
                this.lists = response.data.object
                this.totallength = Math.ceil((this.lists.length-1) / this.dataPerPage) 
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
        checkPrison(list) {

            instanceWithAuth
            .post("report/check/prison", {
                reportedUid : list.reportedUid,
                reporterUid : list.reporterUid
            })
            .then( (response)=>{
                //console.log(response.data.object);
                if(!response.data.object){
                    alert("이미 정지중인 유저입니다.");
                }else{
                    this.insertPrison(response.data.object);
                    this.getReport();
                }
            })
            .catch((response)=>{
                alert("xx.");
            })
        },
        insertPrison(report) {
            // console.log("report => " + report[0].rid);
            instanceWithAuth
            .post("report/prison", {
                rid : report[0].rid,
                reportedUid : report[0].reportedUid,
                reason : report[0].reason,
            })
            .then( (response)=>{
               alert("10분 정치 처분 받았습니다!!");
            })
            .catch((response)=>{
                alert("신고 접수 중 에러가 발생했습니다.");
            })
        }
        ,
        deleteReport(rid) {
            instanceWithAuth
            .delete('report',{
                params : {
                    rid : rid
                }
            }).then((response)=>{
                alert("신고를 기각했습니다..");
                 this.getReport();
            }).catch((error)=>{
                alert("신고 기각 중 에러가 발생했습니다.");
            })
        },
    },
    data (){
        return {
            lists : [],
            curpage:1,
            dataPerPage : 9,
            totallength : 1,
            startnum : 1,
            endnum: 10,
            uid : 0,
            reportedUid : 0,
            reason :"",
            term : 0,
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
    height:100px;
}
.ndate{
        float:right;
}
.mainnotice{
    font-weight: bold;
}
</style>