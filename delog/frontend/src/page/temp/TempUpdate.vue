<template>
    <div class="detail outCenter" id="write">
        <div class="temp">
                <input v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요"></div>
                <div class="temp">

                    <b>작성일시</b>: {{postlist.create_date | formatDate}}
                </div>
                <div style="margin-top: 50px;" class="temp">

                        <!-- <textarea v-model="contents" class="textbox"
                        placeholder="hello"></textarea> -->
                       <div class="toast-editor" style="background-color:white;">
                            <Editor 
                            ref="toastuiEditor"
                            plugins =[codeSyntaxHightlight]
                            initialEditType= 'markdown'
                            previewStyle= 'tab'
                            height="1000px"
                             v-if="contents!=null" :initialValue="contents"/>
                            </div>
                        <div id="tagset">
                            <div id="writtenTag">
                                <span class="wtags" v-for="(tag,index) in tags" v-bind:key="tag">
                                    <b >
                                        #{{tag}}</b>
                                    <i class="fas fa-times" v-on:click="removeTag(index)"></i>
                                </span>
                            </div>
                            <input
                                v-model="taginput"
                                type="text"
                                id="taginput"
                                class="title"
                                v-on:keyup.13="makeTags"
                                placeholder="#태그입력"></div>
                        </div>

                        <div class="buttons" style="text-align:center;">
                            <v-btn class="btn" @click="insert(1)">
                                <span>임시저장</span>
                            </v-btn>
                            <v-btn class="btn" @click="insert(0)">
                                <span>등록완료</span>
                            </v-btn>
                        </div>
                    </div>
</template>
<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import {Editor} from "@toast-ui/vue-editor";
import { formatDate } from '@/utils/filters';
import {instance, instanceWithAuth} from '@/api/index'

    export default {
        //   <!-- 제목 , 작성자, 작성일시, 내용, 목차 , 댓글리스트   like 기능 ,공유하기 , 임시저장  -->
        components: {
            Editor
        },
        created() {
            this.getBoard();
        },
        methods: {
            insert(is_complete) {
                if (!this.title) {
                    alert("제목 입력해주세요.");
                } else {
                    if (is_complete == 0) 
                        this.makeBoard();
                    else 
                        this.tempUpdate()
                }
            },
            getBoard() {
                instanceWithAuth
                    .get('post/detail/', {
                        params: {
                            pid: this.$route.params.pid
                        }
                    })
                    .then((response) => {
                        // //console.log(response.data.object);
                        this.userlist = response.data.object.user
                        this.postlist = response.data.object.post
                        this.title = this.postlist.title
                        this.contents = this.postlist.contents;
                        this.toastuiEditor =this.contents
                        this.gettags = this.postlist.tags
                        if(this.postlist.hasOwnProperty('tags') == true){
                            this.tags = this.gettags.substring(0,this.gettags.length-1).split(" ");
                            // //console.log("받은 태그들:")
                            // //console.log(this.tags)
                        }                        
                    })
                    .catch((error) => {

                        alert("error!!!" + error)
                    })
                },
            makeBoard() {

                // //console.log("임시저장을 글로 등록")
                instanceWithAuth
                    .put('post/tempchange', {
                       params: {
                           pid :  this.$route.params.pid
                        }
                    })
                    .then((response) => {
                        alert("임시저장글을 게시글로 발행했습니다.");
                        this
                            .$router
                            .push('/');
                    })
                    .catch((response) => {
                        // //console.log(response)
                        alert("다시 입력해주세요.");
                    })
                },
            tempUpdate(){
                for(var i=0 ;i< this.tags.length; i++){
                    this.sendtags += this.tags[i]
                    this.sendtags += ' '
                }    
                // //console.log('태그 무리'+this.sendtags)                 
                // //console.log("임시저장글 update!! ")
                instanceWithAuth
                    .put('post/temp', {
                        pid :  this.$route.params.pid,
                        writer: this.userlist.nickname,
                        title: this.title,
                        tags: this.sendtags,
                        contents: this.contents
                        //pid의 임시저장 게시물안의 내용 ...
                    })
                    .then((response) => {
                        alert("수정 성공");
                        this.$router.push('/');
                    })
                    .catch((response) => {
                        // //console.log(response)
                        alert("다시 입력해주세요.");
                    })
                },               
            makeTags() {
                if (this.taginput) {
                    // //console.log(this.tags);
                    this
                        .tags
                        .push(this.taginput.trim(" "));
                    this.taginput = "";
                    // //console.log("makeTags : " + this.tags);
                }
            },
            removeTag(index) {
                // //console.log("index : " + index + " before removeTage : " + this.tags);
                this
                    .tags
                    .splice(index, 1);
                // //console.log("index : " + index + " after removeTage : " + this.tags);
            }
        },
        data: () => {
            return {
                create_date: "",
                title: "",
                userlist :[],
                postlist:[],
                writer: "",
                contents: null,
                taginput: "",
                tags: [],
                sendtags: '',
                gettags:'',                
            }
        }
}
</script>
<style scoped> 
.buttons {
    margin-top: 30px;
}
.title {
    margin: 18px auto 17px;
    padding-bottom: 26px;
    height: 56px;
    font-size: 25px;
    border-bottom: 1px solid rgba(0,0,0,.05);
    width: 100%;
}
.textbox {

    border: 1px solid #ccc;
    height: 400px;
}
.temp {
    text-align: left;
    border: 0 solid black;
    margin: 0 auto;
}
#tagset {
    min-height: 100px;
    margin-top: 30px;
}
#taginput{
    border:0px;
    margin:0px;
    padding:0px;
    font-size:13px;
}
#writtenTag {
    min-height: 50px;

}
.wtags {
    margin-right: 4px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 3rem;
}
</style>