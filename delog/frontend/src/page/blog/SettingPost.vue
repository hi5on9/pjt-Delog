<template>
    <div>
        <div class="editwrap">
        <div v-for="list in lists" :key="list.post.pid" class="list">
            <div @click="goDetail(list.post.pid)" class="editcontents">
            {{list.post.title}} 
            <br>
            {{list.post.create_date | formatDate}}
            </div>
            <div class="editset">
            <span class="btn editbtn" @click="btnUpdate(list.post.pid)">수정</span>
            <span class="btn editbtn" @click="btnDelete(list.post.pid)">삭제</span>
            <select class="selected btn editbtn"  @change="showhidden(list.post.pid,$event)" >
                <option value="0" :selected="list.post._hidden == false">공개</option>
                <option value="1" :selected="list.post._hidden == true" >비공개</option>
            </select>
            </div>
        </div>
        <infinite-loading  @infinite="infiniteHandler" spinner="circles">
            <div slot="no-more"></div>
            <div slot="no-results">
                <div style="margin-top:150px">
                <p class="msg2">아직 글을 작성하지 않았습니다.</p>
                </div>
            </div>    
        </infinite-loading>  
        </div>

        <!-- MODAL -->
        <Modal v-if="deleteModal" @close="deleteModal = false" style="padding-top:150px; padding-bottom:100vh;">
            <h3 slot="header">
                정말로 삭제 하시겠습니까?
            </h3>
            <div slot="body">
                <v-btn @click="postDelete()">
                    <span>확인</span>
                </v-btn>
                <v-btn @click="deleteModal = false">
                    <span>취소</span>
                </v-btn>
            </div>
        </Modal>
    </div>
</template>

<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import {instance, instanceWithAuth} from '@/api/index'
import InfiniteLoading  from 'vue-infinite-loading'
import Modal from '@/components/Modal.vue'
export default {
    components:{
        Modal,
        InfiniteLoading
    },
    mounted () {
    },
    methods :{
        goWrite(){
            this.$router.push('../post/write')
        },
        showhidden(p,event){
            // //console.log("pid:"+p+", type:"+event.target.value)
                instanceWithAuth
                .put('post/hidden',{
                        pid: p,
                        type : event.target.value
                })
            .then((response) => {
                // //console.log("--숨기기-- type:"+event.target.value)
                if(event.target.value==1) alert("게시글이 비공개 처리 됐습니다.")
                else ("게시글이 공개 처리 됐습니다.")
                // location.reload()
                // //console.log(response)
            })
            .catch((response) => {
                alert("잠시 후 다시 시도해주세요");
            });
        },
        // post 가져오기
         infiniteHandler($state){
            instanceWithAuth
            .get('post/mypost',{
                params : {
                uid : this.$store.state.uid,
                page: this.page
                }
            })
        .then(({data})=>{
            setTimeout (() =>{
                if(data.object.length){
                    this.page +=1;
                    this.lists = this.lists.concat(data.object)
                    // //console.log(this.lists)
                    $state.loaded()
                
                }else {
                    $state.complete()
                }
            },1000)
                })

        },
        // 버튼 set 
        goDetail(pid){
            this.$router.push('/post/detail/'+pid)
        },
        btnUpdate(pid) {
            // //console.log('게시글 수정 요청 보냄')
            this.$router.push("/post/update/" + pid);
        },
        btnDelete(pid) {
            // 정말로 삭제할 것인지 다시 확인하는 Modal 띄움
            this.deleteModal = !this.deleteModal;
            this.nowpid = pid;
        },
        postDelete() {
            // //console.log('게시글 삭제 요청 보냄')
            instanceWithAuth
            .delete("post/", {
                params: {
                pid: this.nowpid
                },
            })
            .then((response) => {
                alert("삭제 됐습니다!! ");
                location.reload()
            })
            .catch((response) => {
                // //console.log(response);
            });
        },
    },
 
    data () {
        return {
            hlists: [],
            lists : [],
            selected : '',
            deleteModal: false,
            nowpid: 0,
            key : 0,
            page:0

        }
    }
}
</script>

<style scoped>
.editwrap{
    min-height: 350px;
}
.editcontents{
    width:60%;
    float:left;
}
.editset{
    float:right;
    /* display:none; */
}
.editbtn{
    width:66px;
    font-size:14px;
    text-align: center;
}
select {
    appearance: auto;
}
.list{
    border-bottom:1px solid gray;
    min-height: 80px;

}
.list:hover .editset{
    display: block;
  
}
.msg2{
  color:#7e7d7d;
  font-size:13px;
}
/* .list:hover {
      background-color:#F5F5F5;
      color:black;
} */
</style>