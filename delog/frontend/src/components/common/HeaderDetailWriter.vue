<template>
    <div>
        <span>
            <v-btn @click="btnUpdate()">
                <i class="fas fa-edit"></i>
                <!-- 수정하기 아이콘 -->
            </v-btn>
        </span>
        <span>
            <v-btn @click="btnDelete()">
                <i class="far fa-trash-alt"></i>
                <!-- 삭제하기 아이콘 -->
            </v-btn>
        </span>
        <Modal v-if="deleteModal" @close="deleteModal = false" style="padding-top:150px; padding-bottom:100vh; color:black;">
            <h3 slot="header" style="margin-top:50px; margin-bottom:50px;">
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
    import Modal from '@/components/Modal';
    import Vuex from 'vuex';

    export default {
        components: { 
            Modal,
        },
        data() {
           return {
               deleteModal: false,
           }
        },
        methods : {
            btnUpdate() {
                // //console.log('게시글 수정 요청 보냄')
                if(this.$route.name =='noticedetail'){
                    this.$store.dispatch('updatenotice', this.$route)
                }
                else{
                    this.$store.dispatch('articleUpdate', this.$route)
                }
            },
            btnDelete() {
                // 정말로 삭제할 것인지 다시 확인하는 Modal 띄움
                this.deleteModal = !this.deleteModal;
            },
            postDelete() {
                // //console.log('게시글 삭제 요청 보냄')
                if(this.$route.name =='noticedetail'){
                    this.$store.dispatch('deletenotice', this.$route)
                }
                this.$store.dispatch('articleDelete', this.$route)
            },
        }
    }
</script>

<style>

</style>