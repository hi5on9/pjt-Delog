<template>
    <div class="detail outCenter" id="write">
        <div class="temp">
            <input v-if="this.$vuetify.theme.dark == true" v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요" style="color:white;">
            <input v-else v-model="title" type="text" id="title" class="title" placeholder="제목을 입력하세요">
        </div>
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
                        <b v-if="tag!= ''">
                            #{{tag}}</b>
                        &nbsp;
                        <i class="fas fa-times" v-on:click="removeTag(index)" style="cursor:pointer"></i>
                    </span>
                </div>
                <input
                    v-model="taginput"
                    type="text"
                    id="taginput"
                    class="title"
                    v-on:keyup.13="makeTags"
                    placeholder="#태그입력(입력 후 Enter)"
                >
            </div>
            <div style="text-align:center;">
                <div class="btn insertbtn" @click="makeBoard()" style="width:90px;">
                    <span>등 록</span>
                </div>
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
            getBoard() {
                instance
                .get('post/detail', {
                    params: {
                        pid: this.$route.params.id
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
                        // //console.log(this.tags);
                    }
                })
                .catch((error) => {
                    alert("error!!!" + error)
                })
            },
            makeBoard() {
                for(var i=0 ;i< this.tags.length; i++){
                    this.sendtags += this.tags[i]
                    this.sendtags += ' '
                }    
                // //console.log('태그 무리'+this.sendtags)                            
                instanceWithAuth
                .put('post', {
                    pid:this.$route.params.id,
                    writer: this.userlist.nickname,
                    title: this.title,
                    tags: this.sendtags,
                    contents: this.$refs.toastuiEditor.invoke("getMarkdown"),

                })
                .then( (response) => {
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
        watch: {},
        data: () => {
            return {
                title:'',
                userlist :[],
                postlist:[],
                contents: null,
                taginput: "",
                tags: [],
                sendtags: '',
                gettags:'',
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