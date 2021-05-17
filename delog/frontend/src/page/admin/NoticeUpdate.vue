<template>
    <div class="detail" id="write" style="margin-top:100px; margin-bottom: 100px;">
        <div class="temp">
            <i class="fas fa-thumbtack" v-if="list.is_main=='1'" id="mainNotice"></i>
            <i class="fas fa-thumbtack" v-if="list.is_main=='0'" @click="upMain" ></i>
                <input v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요"></div>
                <div class="temp">
                    <b>작성일시</b>: {{list.create_date | formatDate}}
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

                        <div class="buttons" style="text-align:center;">
                            <v-btn class="btn" @click="insert">
                                <span>완료</span>
                            </v-btn>
                        </div>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue'
    import Vuex from 'vuex'
    import "codemirror/lib/codemirror.css";
    import "@toast-ui/editor/dist/toastui-editor.css";
    import {Editor} from "@toast-ui/vue-editor";
    import {instance, instanceWithAuth} from '@/api/index';

    
    export default {
        //   <!-- 제목 , 작성자, 작성일시, 내용, 목차 , 댓글리스트   like 기능 ,공유하기 , 임시저장  -->
        components: {
            Editor
        },
        created() {
            this.getBoard();
        },
        methods: {
            insert() {
                if (!this.title) {
                    alert("제목 입력해주세요.");
                } else {
                    this.makeBoard();
                }
            },
            upMain(){
                instanceWithAuth
                    .put('notice/mainchange', {
                            nid: this.$route.params.nid
                    })
                    .then((response) => {
                        // //console.log(response.data.object);
                        alert('메인 공지를 변경했습니다.')
                    })
                    .catch((error) => {
                        alert("error!!!" + error)
                    })               
            },
            getBoard() {
                instance
                    .get('notice/detail', {
                        params: {
                            nid: this.$route.params.nid
                        }
                    })
                    .then((response) => {
                        // //console.log(response.data.object);
                        this.list = response.data.object
                        this.title = this.list.title
                        this.contents = this.list.contents;
                        this.toastuiEditor =this.contents
                    })
                    .catch((error) => {
                        alert("error!!!" + error)
                    })
                },
            makeBoard() {
                instanceWithAuth
                    .put('notice/', {
                        nid:this.$route.params.nid,
                        title: this.title,
                        contents: this.$refs.toastuiEditor.invoke("getMarkdown"),

                    })
                    .then( (response) => {
                        alert("수정 성공");
                            this.$router.push('/admin/notice');
                    })
                    .catch((response) => {
                        // //console.log(response)
                        alert("다시 입력해주세요.");
                    })
                },

        },
        watch: {},
        data: () => {
            return {
                title:'',
                list: [],
                contents: null,
                taginput: "",
                tags: [],
            };
        }
    };
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
    width: 60%;
    height: 400px;
}
.temp {
    width: 60%;
    text-align: left;
    border: 0 solid black;
    margin: 0 auto;
}
#mainNotice{
    color:#cf1b1b; 
    transform: rotate(45deg);
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