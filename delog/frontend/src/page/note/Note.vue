<template>
  <div class="manage outCenter">
    <p class="titleDoHyeon">쪽지함</p>

    <div style="margin-bottom:30px;  border-top: 3px solid #7e7d7d;">
        <br>
        <div class="tabs">
            <a v-on:click="getNote(2)" v-bind:class="[ activetab === 1 ? 'active' : '' ]">받은 쪽지함</a>
            <a v-on:click="getNote(1)" v-bind:class="[ activetab === 2 ? 'active' : '' ]">보낸 쪽지함</a>
        </div>

    </div>
    <div class="container2" >
        <div style="min-height:400px;">
            <ul class="holder" v-if="lists.length !== 0">
                <li two-line v-for="(list,index) in calData()" :key="index">
                    <div class="button" type="button" @click="modalSwtich(index)">
                        <div v-if="menuFlag == 1">
                            <a v-if="list.user.nickname.length > 10" class="nanumGothic" @click="gowriterblog(list.user.uid)" style="margin-right:30px; color:white">
                                {{list.note.sender.substring(0, 10)}}...
                            </a>
                            <a v-else @click="gowriterblog(list.user.uid)" class="nanumGothic" style="margin-right:30px; color:white">
                                {{list.note.sender}}
                            </a>
                        </div>
                        <div v-if="menuFlag == 2">
                            <a v-if="list.user.nickname.length > 10" class="nanumGothic" @click="gowriterblog(list.user.uid)" style="color:white">
                                {{list.note.recipient.substring(0, 10)}}...
                            </a>
                            <a v-else class="nanumGothic" @click="gowriterblog(list.user.uid)" style="margin-right:30px; color:white">
                                {{list.note.recipient}}
                            </a>
                        </div>
                        <a v-if="list.note.contents.length > 30" class="nanumGothic" style="color:white; font-size:1rem;">
                            {{list.note.contents.substring(0, 30)}}...
                        </a>
                        <a v-else class="nanumGothic" style="color:white; font-size:1rem;">
                            {{list.note.contents}}
                        </a>
                        <span class="ndate nanumGothic" style="float:right; margin-top:20px; color:white; font-size:0.875rem;">{{list.note.create_date | formatDate }}</span>

                    </div>
                </li>
            </ul>
        </div>
        <Modal v-if="showModal" @close="showModal = false">
            <h3 slot="header">
                <span class="doHyeon" style="font-size:1.5rem; ">상세 쪽지</span>
                <i class="closeModalBtn fas fa-times" @click="showModal = false" style="float:right; color:black; cursor:pointer"></i>
            </h3>
            <div slot="body" style="margin-top:30px; text-align:left;">
                <div class="nomal-content alim">
                    <div class="name">
                        <div class="img">
                            <div class="mask" style="background-image:url('/edu/comm/imgDownload.do?userId=OMcKE42iH0BE8udOE%2FrWOA%3D%3D');"></div> 
                        </div>
                        <div>
                            <p class="nanumGothic"><span>Who : </span>{{lists[this.detailIndex].note.sender}}</p>
                            <p class="nanumGothic"><span>When : </span>{{lists[this.detailIndex].note.create_date | formatDate }}</p>
                        </div>
                    </div>
                    <div class="showBox nanumGothic">
                        <span>Contents</span>
                        <p>: {{lists[this.detailIndex].note.contents}}</p>
                    </div>
                </div>
            </div>
        </Modal>
        <v-pagination style="background-color:lightgray"
            class = "pnavi" 
            v-model="curpage" 
            :length="totallength">
        </v-pagination >
    </div>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth } from '@/api/index';

export default {
    components:{
        Modal
    },
    created (){
        this.getNote(2);
    },
    methods :{
        gowriterblog(uid) {
            this.$router.push('/blog/' + uid);
        },
        getNote(tmpType){
            if(tmpType == 2){
                this.menuFlag = 1
            } else {
                this.menuFlag = 2
            }
            this.activetab = this.menuFlag;
            instance
            .get('note',{
                params:{
                    uid : this.$store.state.uid,
                    type : tmpType
                }
            })
            .then((response)=>{
                this.lists = response.data.object
                // //console.log(this.lists)
                this.totallength = Math.ceil((this.lists.length-1) / this.dataPerPage) 
                // //console.log("response : "+this.lists.length);
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
        modalSwtich(index) {
            this.detailIndex = index;
            this.showModal = !this.showModal;
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
            showModal:false,
            menuFlag:1,
            activetab :1,
            detailIndex:0
        }
    }
}
</script>

<style scoped>

ul {  padding: 0; list-style-type: none; width:100%;} 
ul li { padding: 20px; font-size: 1.3em; background-color: #E0EDF4; border-left: 5px solid #b3b3b3; margin-bottom: 2px; color: #3E5252; }

 .notice_list{
     margin: 60px auto;
 }
li:hover {
    background-color:#66d9ff;
}
a{
    color:white;
}

.holder {
  text-align: left;
  padding: 0;
  margin: 0;
}

.holder li {
  padding: 1em;
  margin-bottom: .125em;
  display: block;
  list-style: none;
}

.holder li {
  animation: fadeIn 1s ease-in both;
  opacity: 0;
}
.holder li:nth-child(2n) {
  background-color: rgb(99, 98, 98);
}

.holder li:nth-child(2n+1) {
  background-color: rgb(122, 121, 121);
}

.holder li:hover{
    background-color:rgb(163, 52, 204);
}

#btnReload:hover {
  background: #ddd;
}
.tabs {
    overflow: hidden;
    margin:30px auto;

}

.tabs ul {
    list-style-type: none;
    margin-left: 20px;
}

.tabs a{
    float: left;
    cursor: pointer;
    padding: 12px 24px;
    transition: background-color 0.2s;
    border-right: none;
    border-radius: 10px 10px 0 0;
    font-weight: bold;
    color: #484848;
}
/* Change background color of tabs on hover */
/* Styling for active tab */
.tabs a.active {
    color:rgb(163, 52, 204);
    border-bottom:2px solid rgb(163, 52, 204);
    cursor: default;
}
@keyframes fadeIn {
	from {
		opacity: 0;
		transform: translate3d(0, -20%, 0);
	}
	to {
		opacity: 1;
		transform: translate3d(0, 0, 0);
	}
}
</style>