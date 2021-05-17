<template>
    <div class="detail" id="write" style="margin-top:100px; margin-bottom:100px;">
        <div class="temp">
            <input v-if="this.$vuetify.theme.dark == true" v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요" style="color:white;">
            <input v-else v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요">
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
                height="1000px"/>
            </div>
        </div>

        <div class="buttons" style="text-align:center;">
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
    import 'tui-editor/dist/tui-editor.css';
    import 'tui-editor/dist/tui-editor-contents.css';
    import "codemirror/lib/codemirror.css";
    import {Editor} from "@toast-ui/vue-editor";
    import {instance, instanceWithAuth} from '@/api/index';
    export default {
        //   <!-- 제목 , 작성자, 작성일시, 내용, 목차 , 댓글리스트   like 기능 ,공유하기 , 임시저장  -->
        
        components: {
            Editor
        },
        methods: {
            insert(is_complete) {
                if (!this.title) {
                    alert("제목 입력해주세요.");
                }else if(this.$store.state.uid==''){
                    alert("로그인하세요")
                } 
                else {
                    if(is_complete==0) 
                    this.makeBoard();
                    else this.makeTemp()
                }
            },
            makeBoard() {
                instanceWithAuth
                    .post("notice/", {
                        title: this.title,
                        contents: this.$refs.toastuiEditor.invoke("getMarkdown"),
                        is_hidden : 1,
                        is_comment :1,
                        is_category:1
                    })
                    .then( (response)=>{
                        alert("저장 완료")
                       this.$router.push('./');
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

        },
        data: () => {
            return {
                title: "",
                writer:"", 
                content:null,
                taginput: "", 
                tags: [],
            }       
        }
    }
</script>
<style scoped="scoped"> 
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
    width: 60%;
    height: 400px;
}
.temp {
    width: 60%;
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