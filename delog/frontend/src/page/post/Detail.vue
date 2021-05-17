<template>
    <div class="detail outCenter">
        <div class="temp">
            <br>
            <p class="titleDoHyeon">{{ postlist.title }}</p>
            <div @click="gowriterblog" type="button">
                <span class="profilediv">
                    <img class="profileimg" :src="userprofile" style=" border:2px solid black" />
                </span>
                <span class="h6 font-weight-regular"  style="padding-left: 2px; padding-right: 2px;">
                {{ userlist.nickname }}
                </span>
            </div>
            <b class="font-weight-thin font-italic text-body-2"> at </b>
            <span class="font-weight-thin font-italic text-body-2" style="padding-left: 1px;">
                {{postlist.create_date | formatDate}}
            </span>
            <v-card light="light" id="contentbox">
                <Viewer v-if="postlist.contents!=null" :initialValue="postlist.contents"></Viewer>
            </v-card>
            <div id="writtenTag" v-if="postlist.tags != null">
                <span class="wtags" v-for="tag in taglist" :key="tag"   @click="searchhash(tag)">
                    #{{tag}}
                </span>
            </div>
            <br><br><br>
            <Comment></Comment>
        </div>

    </div>
</template>
<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import 'codemirror/lib/codemirror.css'
import "@toast-ui/editor/dist/toastui-editor.css"
import {Viewer} from "@toast-ui/vue-editor"
import Modal from '@/components/Modal.vue' ////
import Comment from '@/page/post/Comment'
import {instance, instanceWithAuth} from '@/api/index'

export default {
    data() {
        return {
            showModal: false, 
            postlist: [],
            userlist:[],
            tags : '',
            taglist : [],
            url: `${process.env.VUE_APP_API_URL}`,
            userprofile: '',
            };

    },
    components: {
        Viewer,
        Modal,
        Comment,
    },
    created() {
        this.getBoard()
        
    
    },
    mounted() {
    },
    methods: {
        searchhash(word){
            this.$ga.event('버튼','해시태그',word)
            // //console.log(this.$ga);
            this.$store.commit('hashsearch',word)
            this.$router.push('../../post/search')
        },
        gowriterblog() {
                this.$router.push('/blog/'+this.userlist.uid);
        },
        getBoard() {
            instance
                .get('post/detail', {
                    params: {
                        pid: this.$route.params.id
                    }
                })
                .then((response) => {
                    this.userlist = response.data.object.user;
                    this.postlist = response.data.object.post;
                    this.$store.commit('idCheck',this.userlist.uid);
                    this.userprofile = this.userlist.profile;
    
                    this.$store.state.shareTitle = this.postlist.title  
                    if(this.postlist.tags == null) {
                        this.tags = "null";
                    }else{
                        this.tags = this.postlist.tags.substring(0, this.tags.length - 1);
                         for(var i =0 ; i<this.taglist.length;i++){
                            this.$store.state.shareTags = '#'+this.taglist[i] + ' '
                        }
                    }
                })
                .catch((error) => {
                    alert("error!!!" + error)
                })
                //좋아요 유무....
                if(this.$store.state.uid != ''){
                instanceWithAuth
                .get('post/like/', {
                    params: {
                        pid: this.$route.params.id,
                        uid: this.$store.state.uid,
                    }
                })
                .then((response) => {
                    // //console.log(response.data.object);
                    if(!response.data.object){
                        this.$store.state.showheart ='1'  
                    } 
                        else if (response.data.object) {
                            this.$store.state.showheart ='2'
                    }
                    // //console.log("get showheart"+this.$store.state.showheart)

                })
                .catch((error) => {
                    alert("error!!!" + error)
                })
                }
            },
            
    },

}
</script>
<style scoped>
    #contentbox {
        margin-top : 50px;
        min-height: 400px;
        border: 1px solid #ccc;
    }
    #commentbox {
        border: 1px solid #ccc;
        width: 80%;
    }
    #writtenTag {
        margin-top: 30px;
    }
    .wtags {
        margin-right: 4px;
        padding: 7.5px;
        border: 1px solid #ccc;
        border-radius: 3rem;
        cursor: pointer;
    }
    .profileimg {
        height: 50px;
        width: 50px;
        border-radius: 150px;
        background-color: blueviolet;
        margin-top:10px;
        margin-bottom:10px;
        margin-right : 20px;
        float : left;
    }
</style>