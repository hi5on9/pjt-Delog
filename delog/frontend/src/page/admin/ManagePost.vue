<template>
  <div class="manage">
        <table  style="width:100%; text-align:center;">
          <thead>
                <th style="width:10%">글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성 날짜</th>
                <th style="width:10%">처리</th>
          </thead>
            <tr v-for="list in lists" v-bind:key="list.uid" class="shadow"  style="width:100%;">
                <td>{{list.post.pid}}</td>
                <td class="h6">{{list.post.title}}</td>
                <td class="h6">{{list.user.nickname}}</td>
                <td class="h6">{{list.post.create_date | formatDate}}</td>
                <td class="removeBtn" v-on:click="removepost(list.post.pid,list.user.uid)" style="cursor:pointer">
                    <i class="fas fa-trash"></i>
                </td>
            </tr>
        </table>


    <Modal v-if="deleteModal" @close="deleteModal = false" style="padding-top:150px; padding-bottom:100vh;">
        <h3 slot="header" style="margin-top:75px;">
            정말로 삭제 하시겠습니까?
        </h3>
        <div slot="body">
            <select style="margin-top:30px;" v-model="value">
                <option disabled value="">사유를 선택해 주세요</option>
                <option value="명예훼손/기타권리 침해">명예훼손/기타권리 침해</option>
                <option value="음란성 게시물">음란성 게시물</option>
                <option value="욕설/반말/부적절한언어">욕설/반말/부적절한언어</option>
                <option value="도배성 게시물">도배성 게시물</option>
            </select>
            <div style="margin-top:90px;">
            <div @click="postDelete()" class="btn postdelbtn">
                <span>확인</span>
            </div>
            <div @click="deleteModal = false" class="btn">
                <span>취소</span>
            </div>
            </div>
        </div>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api';

export default {
    components :{
         Modal,
    },
    created (){
        this.showlist()
    },
    methods :{
        removepost(pid,uid){
            this.selectpost = pid;
            this.thispostowner = uid;
            this.deleteModal = !this.deleteModal;
        },
        postDelete() {
            // //console.log('게시글 삭제 요청 보냄'+this.selectpost)
            instanceWithAuth
            .delete('post/', {
                params: {
                    pid: this.selectpost
                }
            })
            .then((response) => {
                alert("삭제 됐습니다!! ")
                // //console.log('관리자가 게시글 삭제')
                location.reload()
            })
            .catch((response) => {
                // //console.log(response)
            });
            // uid에게 메세지 보내기 !! 
            // //console.log(this.value)
            this.value = '관리자에 의해 '+this.value+ '의 사유로 게시물이 삭제 됐습니다. '
            instanceWithAuth
            .post("note", {
                sender: 1,
                recipient: this.thispostowner,
                contents: this.value
            })
            .then(function() {
                this.$router.push('/');
            })
            .catch(function() {
            });
        },        
        showlist(){
            // //console.log("showlist() : 게시글 정보");
            instance
                .get('post')
                .then((response)=>{
                    // //console.log("response : ");
                    // //console.log(response);
                    this.lists = response.data.object
                })
        },
    },
    data : () => {
        return {
            lists:[],
            deleteModal:false,
            selectpost: '',
            thispostowner: 0,
            value : ''
        }
    }
}
</script>

<style scoped>
select {
    appearance: auto;
    border:1px solid gray;
}
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
.postdelbtn:hover{
    background-color:#de4343;
    color:white;
}

</style>