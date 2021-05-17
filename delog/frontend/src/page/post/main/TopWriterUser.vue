<template>
    <div class="topUser">
        <div style="width:60%; margin:0 auto;">
            <div>
                <span class="titleDoHyeon">B E S T</span>
                <span class="titleDoHyeon" style="margin-left:40px;">U S E R</span>
                <br>
                <span id="decs">&#x1F451;명예의 전당&#x1F451;</span>
            </div>
            <button @click="goblog(list.uid)" v-for="(list,index) in lists" :key="list.uid"  class="users" style="margin-top:50px;">
                <div class="profilediv">
                    <img class="profileimg" :src="list.profile" style=" border:2px solid black" />
                </div>
                <!-- <div style="text-align:center; margin-top:20px;" @click="goblog(list.uid)">
                    <i class="far fa-user fa-5x" type="button"></i>
                </div> -->
                    
                <p class="doHyeon" style="font-size:1.75rem; text-align:center; margin:5px 0px 15px 0px; cursor:pointer;">{{list.nickname}}</p>
                <div v-if="index == 0">
                    {{getHash(lists[0].uid)}}
                    
                    <div v-if="hash1.length>0" style="text-align:center;">
                        <span v-for="(h1,index) in hash1" :key="h1" style="margin: 5px;">
                            <v-btn x-small rounded color="#5E5F5E" dark @click.stop="searchhash(hash1[index])">#{{ hash1[index] }}</v-btn>
                        </span>
                    </div>
                </div>
                <div v-if="index == 1" style="text-align:center;">
                    {{getHash(lists[1].uid)}}
                    <div v-if="hash2.length>0 ">
                    <span v-for="(h2,index) in hash2" :key="h2" style="margin: 5px;">
                        <v-btn x-small rounded color="#5E5F5E" dark @click.stop="searchhash(hash2[index])">#{{ hash2[index] }}</v-btn>
                    </span>
                    </div>
                </div>
                <div v-if="index == 2" style="text-align:center;">
                    {{getHash(lists[2].uid)}}
                    <div v-if="hash3.length>0">
                    <span v-for="(h3,index) in hash3" :key="h3" style="margin: 5px;">
                        <v-btn x-small rounded color="#5E5F5E" dark @click.stop="searchhash(hash3[index])">#{{ hash3[index] }}</v-btn>
                    </span>
                    </div>
                </div>
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
            hash1 : [],
            hash2: [],
            hash3: [],
            count : 0,
            url: `${process.env.VUE_APP_API_URL}`
        }
    },
    created(){
        this.getTopwriter()
    },
    methods:{
        searchhash(word){
            this.$ga.event('버튼','해시태그',word)
            // //console.log(this.$ga);
            this.$store.commit('hashsearch',word)
            this.$router.push('post/search')
        },
        getTopwriter(){
            // //console.log("---게시글을 많이 쓴 작가 3명---")
                instance
                .get('main/list/order/postcnt')
                .then((response)=>{
                this.lists = response.data.object
                // //console.log(response)
                }).catch((error)=>{
                alert('다시 시도해 주세요!')
            })
        },
        getHash(uid){
                // //console.log("---해시태그 가져오긩---")
                instance
                .get('user/hashtag/fields',{
                    params :{
                        uid:uid 
                    }
                })
                .then((response)=>{
                    // console.log('count:');
                    // console.log(this.count);
                    // console.log(response);
                if(this.count == 0){
                    this.hash1 = response.data.object
                    this.count++;
                }else if (this.count == 1){
                    this.hash2 = response.data.object
                    this.count++;
                }else if (this.count == 2){
                    this.hash3 = response.data.object
                    this.count++;
                }
                

                // //console.log(response)
                }).catch((error)=>{
                alert('다시 시도해 주세요!')
            })
        },
        goblog(uid){
            // //console.log('---GoBlog---')
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
    min-height: 500px;
    /* border-bottom: 1px solid; */
}
#decs{
    font-size:13px;
}
.users{
    display:inline-block;
    width:25%;
    min-height:330px;
    min-width: 200px;
    margin:0 4%;
    text-align:center;
    border-radius: 10px;
    background-color: #00bdaa;
    padding: 20px;
    vertical-align:middle;
    transition: opacity 2s cubic-bezier(0.165, 0.84, 0.44, 1);
}
.users:hover, .users:focus {
  transform: scale3d(1.009, 1.009, 1); 
}
.users:hover::after, .users:focus::after {
  opacity: 1;
}

.profileimg {

        height: 140px;
        width: 140px;
        border-radius: 50%;
        background-color: blueviolet;
        margin : 16px auto;
    }

/* 
#00bdaa : 배경 다크그린 톤
#5E5F5E : 버튼 그레이 톤
 */

</style>