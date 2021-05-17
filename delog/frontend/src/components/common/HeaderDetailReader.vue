<template>
    <div>
        <span>
            <v-btn >
                <i v-if="$store.state.showheart=='1'" class="far fa-heart" @click="setLike" id="empty_heart" ></i>
                <i v-if="$store.state.showheart=='2'" class="fas fa-heart" @click="setUnlike" id="full_heart" style="color:red;"></i>
                <!-- 좋아요 아이콘 -->
            </v-btn>
        </span>
        <span>
            <v-btn @click="focusComment">
            <i class="far fa-comment-dots"></i>
            <!-- 코멘트 작성 아이콘 -->
            </v-btn>
        </span>
        <span>
            <v-btn >
            <i class="fas fa-share-alt" @click="openbox"></i>
            <!-- 공유하기 아이콘 -->
            </v-btn>
             <div id="sharebox" v-if="openshare==true">
                <img @click="kakaoShare" src="@/assets/img/sns_ka.png" width="35"  type="button"/>
                <a href="#" onclick="javascript:window.open('http://share.naver.com/web/shareView.nhn?url=' +encodeURIComponent(document.URL)+'&title='+encodeURIComponent(document.title), 'naversharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Naver" >
                    <img src="@/assets/img/sns_naver.png"  width="35">
                </a>
                <a href="#" onclick="javascript:window.open('https://twitter.com/intent/tweet?text=[%EA%B3%B5%EC%9C%A0]%20' +encodeURIComponent(document.URL)+'%20-%20'+encodeURIComponent(document.title), 'twittersharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Twitter" >
                    <img src="@/assets/img/sns_tw.png"  width="35">
                </a>
             </div>
        </span>
    </div>
</template>
<script>
    import Vue from 'vue'
    import Vuex from 'vuex'
    import axios from 'axios'
    import {instance, instanceWithAuth} from '@/api/index'
    export default {
        methods: {
            openbox(){
                if(this.openshare==true) this.openshare = false
                else this.openshare = true
            },
            kakaoShare(){
                if (window.Kakao && window.Kakao.Auth === undefined) {
                Kakao.init(c98cf0081a45c1eec9c53027fcae4a4c);
                }
                Kakao.Link && Kakao.Link.sendDefault({
                    objectType: 'feed',
                    content: {
                        title: this.$store.state.shareTitle,
                        description: this.$store.state.shareTags,
                        imageUrl: '/img/card1.7354c72a.jpg',
                        link: {
                            mobileWebUrl: 'http://i3d103.p.ssafy.io/post/detail/' + this.$route.params.id,
                            webUrl: 'http://i3d103.p.ssafy.io/post/detail/' + this.$route.params.id
                        },
                    },
                    buttons:[{
                            title: '웹으로 보기',
                            link:{
                                mobileWebUrl: 'http://i3d103.p.ssafy.io/post/detail/' + this.$route.params.id,
                                webUrl: 'http://i3d103.p.ssafy.io/post/detail/' + this.$route.params.id
                            }
                        }],
                    fail: function () {
                        DMAlert('danger', 2, 'Error.');
                    }
                });
            },
            focusComment() {
                this.$store.dispatch('commentFocus')
            },
            setLike() {
                // //console.log(this.$store.state.uid +"이 게시물 "+this.$route.params.id+"에 좋아요")
                if (this.$store.state.uid == "" ) {
                    alert("로그인이 필요합니다!");
                } else {
                    instanceWithAuth
                    .post('post/like/',{
                        pid : this.$route.params.id,
                        uid: this.$store.state.uid
                    })
                    .then((response)=>{
                        // //console.log("이 게시물이 좋아용")
                        this.$store.state.showheart = '2'
                    })
                    .catch((error) => {
                        // //console.log(error);
                    })
                }

            },
               setUnlike() {
                // console.log(this.$store.state.uid +"이 게시물 "+this.$route.params.id+"에 좋아요 해제")
                instanceWithAuth
                    .delete('post/unlike', {
                          params :{
                            pid: this.$route.params.id,
                            uid: this.$store.state.uid
                          }
                    })
                    .then((response) => {
                        // console.log("좋아요 취소!")
                        this.$store.state.showheart = '1'
                    })
                    .catch((error) => {
                        // console.log(error);
                    })
            },
        },
        data : () =>{
            return {
                openshare :false,
            }
        }
    }
</script>

<style>
#sharebox{
    padding: 0.3rem 1rem;
    width:190px;
    height:70px;
    background-color: rgba(208, 208, 208, 0.571);
    position:absolute;
    line-height: 5;
    transition: all 0.3s;
    border-radius: 10px;
    margin-top:5px;
}
#sharebox img {
   width:30%;
   margin-right:5px;
   border-radius: 40px;
   margin-top:7px;
}
</style>