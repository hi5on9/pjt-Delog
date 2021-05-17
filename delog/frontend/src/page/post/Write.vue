<template>
    <div class="detail outCenter" id="write">
        <div class="temp">
            <input v-if="this.$vuetify.theme.dark == true" v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요" style="color:white;">
            <input v-else v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요">
        </div>

        <div style="margin-top: 50px;" class="temp" >
             

            <!-- <textarea v-model="contents" class="textbox"
            placeholder="hello"></textarea> -->
            <div class="toast-editor" style="background-color:white; height:1000px">
            <Editor 
            ref="toastuiEditor"
            plugins =[codeSyntaxHightlight]
            initialEditType= 'markdown'
            previewStyle= 'tab'
            height="1000px"
             />
            </div>
            <div id="tagset">
                <div id="writtenTag">
                    <span class="wtags" v-for="(tag,index) in tags" v-bind:key="tag">
                        <b>#{{tag}}</b>
                        <i class="fas fa-times" v-on:click="removeTag(index)"></i>
                    </span>
                </div>
                <input
                    v-if="this.$vuetify.theme.dark == true"
                    v-model="taginput"
                    type="text"
                    id="taginput"
                    class="title"
                    v-on:keyup.13="makeTags"
                    placeholder="#태그입력"
                    style="color:white;">
                <input
                    v-else
                    v-model="taginput"
                    type="text"
                    id="taginput"
                    class="title"
                    v-on:keyup.13="makeTags"
                    placeholder="#태그입력">
            </div>
        </div>
<!-- 
        <div class="buttons" style="text-align:center;">
            <div class="btn">
                <span @click="tempInsert" class="tempbtn">임시저장</span>
                <span @click="$router.push('../temp/templist')" class="tempsize">{{this.tempsize}}</span>
            </div>
            <div class="btn insertbtn" @click="postInsert">
                <span>등록 완료</span>
            </div>
        </div> -->


        
    </div>
</template>


<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import {Editor} from "@toast-ui/vue-editor";
    import colorSyntaxPlugin from "@toast-ui/editor-plugin-color-syntax";
    import codeSyntaxHightlight from '@toast-ui/editor-plugin-code-syntax-highlight';
    import hljs from 'highlight.js';

    import 'highlight.js/styles/github.css';
    import 'codemirror/lib/codemirror.css';
    import '@toast-ui/editor/dist/toastui-editor.css';
    import 'tui-color-picker/dist/tui-color-picker.css';
    import '@toast-ui/editor/dist/toastui-editor.css';
    import {instance, instanceWithAuth} from '@/api/index';

    import EventBus from '../../eventbus/eventbus'


    export default {
        //   <!-- 제목 , 작성자, 작성일시, 내용, 목차 , 댓글리스트   like 기능 ,공유하기 , 임시저장  -->
        components: {
            Editor 
        },
        created () {
            this.getTemplist()
        },
        mounted(){
            EventBus.$once('insertTemp', this.tempInsert);
            EventBus.$once('insertPost', this.postInsert);
        },
        methods: {
            tempInsert() {
                if (this.title=='') {
                    alert("제목 입력해주세요.");
                }else if(this.$store.state.uid==''){
                    alert("로그인하세요")
                } 
                else {
                    this.makeTemp();
                }
            },
            postInsert() {
                if (!this.title) {
                    alert("제목 입력해주세요.");
                }else if(this.$store.state.uid==''){
                    alert("로그인하세요")
                } 
                else {
                    this.makeBoard();
                }
            },
            getTemplist(){
            instanceWithAuth
            .get('post/templist',{
                params:{
                    uid : this.$store.state.uid
                }
            }).then((response)=>{
                // //console.log("---임시저장 게시물 --")
                this.tempsize = response.data.object.length
                // //console.log(response)

            }).catch((error)=>{
                alert("잠시 후 다시 시도해주세요")
            })
          },
            makeTemp() {
                if(this.tempsize >=100){
                    alert('100개 이상 임시저장 할 수 없습니다!')
                }
                else{
                for(var i=0 ;i< this.tags.length; i++){
                    this.sendtags += this.tags[i]
                   this.sendtags += ' '
               }
                instanceWithAuth
                    .post('post/temp', {
                        writer :  this.$store.state.uid,
                        title: this.title,
                        tags: this.sendtags,
                        contents: this.$refs.toastuiEditor.invoke("getMarkdown"),
                    })
                    .then( (response)=>{
                        alert("임시저장 완료")
                       this.$router.push('./../');
                    })
                    .catch((response)=>{
                        //   //console.log(response)
                        alert("다시 입력해주세요.");
                    })
                }
            },
            makeBoard() {
               for(var i=0 ;i< this.tags.length; i++){
                   this.sendtags += this.tags[i]
                   this.sendtags += ' '
               }
                instanceWithAuth
                    .post("post", {
                        writer :  this.$store.state.uid,
                        title: this.title,
                        tags: this.sendtags,
                        contents: this.$refs.toastuiEditor.invoke("getMarkdown"),
                    })
                    .then( (response)=>{
                        alert("저장 완료")
                       this.$router.push('./../');
                    })
                    .catch((response)=>{
                        //   //console.log(response)
                        alert("다시 입력해주세요.");
                    })
            },
            createAction() {
                var content = this
                    .$refs
                    .toastuiEditor
                    .invoke("getMarkdown"); // content를 저장하는 액션 처리
            },
            makeTags() {
                if (this.taginput) {
                    if(this.tagcount>=10){
                        alert('태그를 10개 이상 입력할 수 없습니다')
                    }else{
                        this.tagcount ++;
                        this
                            .tags
                            .push(this.taginput.trim(" "));
                        this.taginput = "";
                    }
                }
            },
            removeTag(index) {
                this
                    .tags
                    .splice(index, 1);
                this.tagcount--;
            },
            onFileChange(e) { //이미지 첨부했을 때
              var files = e.target.files || e.dataTransfer.files
              if (!files.length) {
                return
              }
              this.createImage(files[0])
            },
            createImage(file) { //이미지 가져오기
                var reader = new FileReader()
                var vm = this
                reader.onload = (e) => {
                vm.userImage = e.target.result
              }
              reader.readAsDataURL(file)
            },
        },
        data: () => {
            return {
                title: "",
                writer:"", 
                content:null,
                taginput: "", 
                tags: [],
                sendtags : '',
                tempsize : 0,
                tagcount:0
            }       
        }
    }
</script>

<style scoped> 
.buttons {
    width:40%;
    margin:30px auto;
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
    font-size:12px;
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
.tempbtn{
    margin-right:15px;
    padding-right:15px;
    border-right:2px solid #fff;
}
.tempsize{
    color:rgb(163, 52, 204);
}
</style>
