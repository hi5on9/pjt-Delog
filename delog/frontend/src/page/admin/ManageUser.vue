<template>
  <div class="manage">
      <table  style="width:100%; text-align:center;">
          <thead>
                <th style="width:10%">번호</th>
                <th>이름</th>
                <th>이메일</th>
                <th>가입 날짜</th>
                <th style="width:10%">처리</th>
          </thead>
            <tr v-for="list in lists" v-bind:key="list.uid" class="shadow"  style="width:100%;">
                <td>{{list.uid}}</td>
                <td class="h6">{{list.nickname}}</td>
                <td class="h6">{{list.email}}</td>
                <td class="h6">{{list.create_date.substring(0,10)}}</td>
                <td class="removeBtn" v-on:click="removeUser(list.uid,list.nickname)" style="cursor:pointer">
                    <i class="fas fa-trash"></i>
                </td>
            </tr>
        </table>
    

    <Modal v-if="deleteModal" @close="deleteModal = false" style="padding-top:150px; padding-bottom:100vh;">
        <h3 slot="header">
            회원 <b>{{this.selectusername}}</b> 님을 탈퇴 시키겠습니까
        </h3>
        <div slot="body">
            <v-btn @click="userDelete()">
                <span>확인</span>
            </v-btn>
            <v-btn @click="deleteModal = false">
                <span>취소</span>
            </v-btn>
        </div>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api/index';

export default {
    components :{
         Modal,
    },
    created (){
        this.showlist()
    },
    methods :{
        removeUser(uid,nickname){
            this.selectuser = uid;
            this.selectusername = nickname
            this.deleteModal = !this.deleteModal;
        },
        userDelete() {
            // //console.log('회원 탈퇴 요청 보냄'+this.selectuser)
              instanceWithAuth
                .delete('user/account', {
                  params: {
                        uid : this.selectuser
                    }
                })
                .then((response) => {
                    alert("해당 회원 정보가 삭제되었습니다. ")
                    this.deleteModal = !this.deleteModal;
                    this.showlist()
                })
                .catch((response) => {
            });
        },        
        showlist(){
            // //console.log("showlist() : 회원정보");
            instanceWithAuth
                .get('user/list')
                .then((response)=>{
                    // //console.log("response : ");
                    // //console.log(response);
                    this.lists = response.data.object
                }).catch((error)=>{
                    // //console.log(error);
                })
        },
    },
    data : () => {
        return {
            lists:[],
            deleteModal:false,
            selectuser: '',
            selectusername: ''
        }
    }
}
</script>

<style scoped>

  .header{
    border-bottom:3px solid #7e7d7d;
    /* width: 60%; */
    margin:0 auto;
    padding-bottom:30px;
    margin-bottom:40px;

}
table{
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
    border: 1px solid #ccc;
    margin: 20px 10px;
}
thead{
    border-right: 1px solid #ccc;
    border-left: 1px solid #ccc;
    background: #7e7d7d;
}
thead th{
    height: 60px;
    padding: 5px;
    font-weight: bold;
    color: #fff;
}

tbody th{
    width: 150px;
    padding: 10px;
    font-weight: bold;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    background: #fcf1f4;
    transition: all 0.3s;
}
td{
    width: 350px;
    padding: 10px;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
}

.removeBtn{
    margin-left: auto;
    color: #de4343;
}
.checkBtn{
    line-height: 45px;
    color: #62acde;
    margin-right: 5px;
}
.checkBtnCompleted{
    color: #b3adad;
}
.textCompleted{
    text-decoration:line-through;
    color:#b3adad;
}

</style>