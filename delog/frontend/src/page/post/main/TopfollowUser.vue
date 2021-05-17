<template>
    <div class="topUser">
        <div style="width:60%; margin:0 auto;">
            <div>
                <span class="titleDoHyeon">D E L O G</span>
                <span class="titleDoHyeon" style="margin-left:40px;">D E V E L O P E R</span>
                <p id="decs">Delog 추천 개발자</p>
            </div>
            <button v-for="list in lists" :key="list.uid"  class="users" @click="goblog(list.uid)">
                <div class="profilediv">
                    <img class="profileimg" :src="list.profile" style=" border:2px solid black" />
                </div>
                <!-- <div style="text-align:center; margin-top:30px;">
                <i class="far fa-user fa-5x" type="button"></i>
                </div> -->
                <p class="doHyeon" style="font-size:1.75rem; text-align:center; margin:30px 0px;">{{list.nickname}}</p>
                <!-- {{ list }} -->
            </button>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import {instance} from '@/api/index'
export default {
    data(){
        return {
            lists : [],
             url: `${process.env.VUE_APP_API_URL}`
        }
    },
    created(){
        this.getTopfollow()
    },
    methods:{
        getTopfollow(){
            // //console.log("---팔로우  많은 작가 6명---")
                instance
                .get('main/list/order/follower')
                .then((response)=>{
                this.lists = response.data.object
                }).catch((error)=>{
                alert('다시 시도해 주세요!')
            })
        },
        goblog(uid){
            // //console.log('---Go My Blog---')
            this.$store.commit('getowner',uid)
            this.$router.push('/blog/'+ uid);
        },
    } // methods end
}
</script>

<style scoped>
.topUser{
    margin:30px;
    margin-top:50px;
    padding-bottom: 30px;
    text-align: center;
    min-height: 400px;
    /* border-bottom: 1px solid; */
}

#decs{
    font-size:13px;
}

.users{
    display:inline-block;
    width:25%;
    height:200px;
    min-width: 150px;
    margin:22px;
    text-align:center;
    border-radius: 10px;
    background-color: #00bdaa;
}

.profileimg {

        height: 100px;
        width: 100px;
        border-radius: 50%;
        background-color: blueviolet;
        margin : 8px auto;
    }

.users:hover {
  /* border-color: #F44336;
  color: #F44336; */
  animation: shakeThatBooty 0.3s linear 1;
  -webkit-animation: shakeThatBooty 0.3s linear 1;
  -moz-animation: shakeThatBooty 0.3s linear 1;
  -o-animation: shakeThatBooty 0.3s linear 1;
  -ms-animation: shakeThatBooty 0.3s linear 1;
}

@keyframes shakeThatBooty {
  33% {
    transform: rotateZ(10deg);
    -webkit-transform: rotateZ(10deg);
    -moz-transform: rotateZ(10deg);
    -o-transform: rotateZ(10deg);
    -ms-transform: rotateZ(10deg);
  }
  67% {
    transform: rotateZ(-10deg);
    -webkit-transform: rotateZ(-10deg);
    -moz-transform: rotateZ(-10deg);
    -o-transform: rotateZ(-10deg);
    -ms-transform: rotateZ(-10deg);
  }
  100% {
    transform: rotateZ(10deg);
    -webkit-transform: rotateZ(10deg);
    -moz-transform: rotateZ(10deg);
    -o-transform: rotateZ(10deg);
    -ms-transform: rotateZ(10deg);
  }
}
</style>