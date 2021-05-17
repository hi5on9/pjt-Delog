<template>
    <div class="outCenter">
        <div class="temp">
            <br>
            <p class="font-weight-medium titleDoHyeon" style="font-size:30px">{{ list.title }}</p>
            <span class="font-weight-thin font-italic text-body-2">by&emsp;</span>
            <span class="h6 font-weight-regular" @click="goNotice" style="padding-left: 2px; padding-right: 2px; cursor:pointer;">
               De-log팀
            </span>
            <b class="font-weight-thin font-italic text-body-2"> at </b>
            <span class="font-weight-thin font-italic text-body-2" style="padding-left: 1px;">
                {{list.create_date | formatDate}}
            </span>

            <v-card light="light" id="contentbox" style="margin-top:25px; margin-bottom:25px;">
                <Viewer v-if="list.contents!=null" :initialValue="list.contents"></Viewer>
            </v-card>
            
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
            list: []
        };
    },
    components: {
        Viewer,
        Modal,
        Comment
    },
    created() {
        this.getBoard()
        
    
    },
    mounted() {
    },
    methods: {
        goNotice() {
             this.$router.push('/admin/notice');
        },
        getBoard() {
            instance
                .get('notice/detail', {
                    params: {
                        nid: this.$route.params.nid
                    }
                })
                .then((response) => {
                    // //console.log(response);
                    this.list = response.data.object;
                    this.$store.commit('idCheck',1)
                })
                .catch((error) => {
                    alert("error!!!" + error)
                })
                //좋아요 유무....
                if(this.$store.state.uid != ''){
                instanceWithAuth
                .get('post/like/', {
                    params: {
                        pid: this.$route.params.nid,
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
        min-height: 400px;
        border: 1px solid #ccc;
    }
    #commentbox {
        border: 1px solid #ccc;
        width: 80%;
    }
</style>