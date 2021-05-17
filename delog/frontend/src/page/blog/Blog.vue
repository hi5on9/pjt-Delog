<template>
<div>
        <blog-profile></blog-profile>
<div class="center">
        <div style="height:180px; padding:0 30px;width:90%;margin:0 auto; border-bottom:1px solid #f5f5f5; ">
        <post-grass/>
        </div>
        <div class="card-wrapper" v-if="lists.length!=0">
            <div class="blog-card" v-for="list in lists" :key="list.post.pid">
                <div class="blog-card__thumbnail">
                    <img src="@/assets/img/card2.jpg" v-on:click="goDetail(list.post.pid)"></div>

                    <div class="blog-card__details">
                            <div class="blog-card__title" v-on:click="goDetail(list.post.pid)">
                                {{list.post.title}}
                                <span v-if="isMine" class="locker">
                                    <i v-if="!list.post._hidden" class="fas fa-lock-open"></i>
                                    <i v-if="list.post._hidden" class="fas fa-lock"></i>
                                </span>
                            </div>

                            <div class="blog-card__description">
                                {{list.post.contents.substring(0, 30)}}
                                ...
                            </div>

                        <div class="tagset" v-if="list.post.tags!=null">
                            <!-- <span style="display:none">
                            {{taglist = (''+list.post.tags).substring(0,list.post.tags.length - 1).split(" ")}}
                            </span> -->
                            
                            <span v-if="list.post.tags.length != 0">
                            <v-btn x-small rounded v-for="tag in list.post.tags.substring(0,list.post.tags.length - 1).split(' ')" :key="tag" class="wtags" @click="searchhash(tag)">
                                #{{tag}}
                            </v-btn>
                            </span>
                        </div>

    

                        <div class="writeinfo">
                            {{list.post.create_date | formatDate}}
                        </div>
                    </div>
                </div> 
                <!-- v-for end -->
        </div>
        


        <infinite-loading @infinite="infiniteHandler" spinner="circles">
            <div slot="no-more"></div>
            <div slot="no-results">
                <div class="card-wrapper">
                    <div class ="inside" v-if="!isMine">
                    <p class="msg1">아직 작성된 글이 없습니다</p>
                        <p class="msg2">
                        홈에서 좋은 글을 만나고 <br>
                        마음에 드는 디로거를 구독하세요
                    </p>
                    <div class="btn btnhome" @click="goHome"><span>홈으로</span></div>
                    </div>
                    <div class ="inside" v-if="isMine">
                        <p class="msg1">아직 작성된 글이 없습니다</p>
                        <p class="msg2">
                        당신의 개발 이야기로 나만의 공간을 채워보세요!
                        </p>
                        <div class="btn btnhome" @click="goWrite"><span>글 쓰기</span></div>
                    </div>
                </div>
            </div>
        </infinite-loading>

    </div>
</div>
</template>
<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import BlogProfile from './BlogProfile.vue'
    import {instance, instanceWithAuth} from '@/api/index'
    import InfiniteLoading  from 'vue-infinite-loading'
    import PostGrass from './PostGrass.vue'

    export default {
        components: {
            BlogProfile,
            InfiniteLoading,
            PostGrass
        },
        watch: {
            '$route' (to, from) { 
            this.lists = []
            this.owner = ""
            this.page =0
            location.reload()
            }
        },
        created () {
            if(this.$route.params.uid == this.$store.state.uid){
                    //블로그 주인이 나라면 lists에 숨긴글 추가...
                        this.getHide()
                        this.isMine = true
            }
        },
        methods: {
            goWrite(){
                this.$router.push('../../post/write')
            },
            goHome(){
                this.$router.push('.././')
            },
            searchhash(word){
                this.$store.commit('hashsearch',word)
                this.$router.push('/post/search')
            },
            goDetail(pid){
               this.$router.push('/post/detail/'+pid)
            },
            getHide(){
                // //console.log("--숨긴글 가져오기--")
                 instanceWithAuth
                 .get('post/hidelist',{
                     params: {
                         uid : this.$store.state.uid
                     }
                 })
                .then((response) => {
                    // //console.log("---숨긴글---")
                    this.hlists = response.data.object
                    // //console.log(this.hlists)
                })
                .catch((response) => {
                    alert("잠시 후 다시 시도해주세요");
                });
            },
            infiniteHandler($state){
                this.owner = localStorage.getItem('blogowner')
                instanceWithAuth
                .get('post/search',{
                    params : {
                        page : this.page,
                        type : 4,
                        value : this.owner
                    }
                }).then(({data})=>{
                        if(data.object.length){
                            this.page +=1;
                            this.lists = this.lists.concat(data.object)
                            //console.log(this.lists)
                            $state.loaded()
                        
                        }else {
                            $state.complete()
                        }
                })

            }
        },
        data : () =>{
            return {
                owner: "",
                lists : [],
                hlists :[],
                isMine : false,
                tags : '',
                taglist : [],
                page :0

            }
        }
    }
</script>

<style lang="scss" scoped>
@import '@/assets/css/blog.scss'

</style>