<template>
      <div class="container1">
        <div class="manageset">
            <p class="titleDoHyeon">사이트 관리</p>
            <div class="tabs1">
                <a v-on:click="tabChange(1)" v-bind:class="[ activetab === 1 ? 'active' : '' ]">게시글 관리</a>
                <a v-on:click="tabChange(2)" v-bind:class="[ activetab === 2 ? 'active' : '' ]">회원 관리</a>
            </div>
            
            <div v-if="activetab===1"><manage-post/></div>
            <div v-else-if="activetab===2"><manage-user/></div>
        </div>
        
    </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import ManagePost from './ManagePost.vue'
import ManageUser from './ManageUser.vue'
export default {
    components : {
        ManagePost,
        ManageUser
    },
    data () {
        return {
            activetab :1,
        }
    },
        methods : {
        tabChange(tabNum){
            this.activetab = tabNum;
        }
    }
}
</script>

<style scoped>
.container1{
    margin:0 auto;
    width:60%;
    margin-top:80px;
}
.manageset{
    margin:0 auto;
    width:80%;
}
.tabs1 {
    overflow: hidden;
    margin:30px auto;

}

.tabs1 ul {
    list-style-type: none;
    margin-left: 20px;
}

.tabs1 a{
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
.tabs1 a.active {
    color:rgb(163, 52, 204);
    border-bottom:2px solid rgb(163, 52, 204);
    cursor: default;
}
</style>