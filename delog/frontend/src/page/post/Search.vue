<template>
    <div id="tabs" class="container"  style="margin-top:100px;">
        <div class="inputbox">
            <input v-if="this.$vuetify.theme.dark == true" type="text" v-model="value" v-on:keyup.13="dosearch" class="inputValue" placeholder="검색어를 입력해 주세요." maxlength="20" onfocus="this.value = this.value;" autocomplete="off" style="color:white;">
            <input v-else type="text" v-model="value" v-on:keyup.13="dosearch" class="inputValue" placeholder="검색어를 입력해 주세요." maxlength="20" onfocus="this.value = this.value;" autocomplete="off">
        </div>
        <div class="tabs">
            <a v-on:click="tabChange(2)" v-bind:class="[ activetab === 2 ? 'active' : '' ]">제목</a>
            <a v-on:click="tabChange(3)" v-bind:class="[ activetab === 3 ? 'active' : '' ]">내용</a>
            <a v-on:click="tabChange(4)" v-bind:class="[ activetab === 4 ? 'active' : '' ]">작성자</a>
            <a v-on:click="tabChange(1)" v-bind:class="[ activetab === 1 ? 'active' : '' ]">해쉬태그</a>
        </div>

            
        <div class="content">
            <div class="tabcontent" >
                <ol  class="rectangle-list" v-if="value!=''">
                    <div>
                        글 검색 결과 {{datasize}}건
                    </div>
                    <div v-if="lists!=null">
                        <li :key="index" v-for="(list,index) in lists">
                            <div v-on:click="goDetail(list.post.pid)" type="button" class="searchlists"> 
                                <div>
                                    <!-- <p class="searchtitle h6">{{list.post.title}}</p> -->
                                </div>
                                <div class="searchcontents subtitle">
                                    {{list.post.contents.substring(0,400)}}
                                    <br>
                                </div>
                                <div style="margin-top:15px;">
                                    <!-- <span>{{list.post.hit}}</span> -->
                                    <div style="min-height:30px;">
                                        <div v-if="list.post.tags">
                                            <!-- <span style="display:none">
                                                {{taglist = (''+list.post.tags).substring(0,list.post.tags.length - 1).split(" ")}}
                                            </span> -->
                                            
                                            <span>
                                                <v-btn x-small rounded v-for="tag in list.post.tags.substring(0,list.post.tags.length - 1).split(' ')" :key="tag" style="margin:5px;">
                                                    #{{tag}}
                                                </v-btn>
                                            </span>
                                        </div>
                                    </div>
                                    <div style="margin:10px 0px;">
                                        <span class="font-weight-thin font-italic text-body-2"  style="margin-top:10px;">by&emsp;</span>
                                        {{list.user.nickname}}
                                    </div>
                                    <p >{{list.post.create_date | formatDate }}</p>
                                </div>
                                <img>
                            </div>
                        </li>
                    </div>
                </ol>
            </div>
        </div>
        <infinite-loading :identifier="infiniteId" v-if="value != ''" @infinite="infiniteHandler" spinner="circles">
            <div slot="no-more">
                <p class="msg2">
                    더 이상 검색 결과가 없습니다.
                </p>
            </div>
            <div slot="no-results">
                <p class="msg2" style="font-size:18px;"><span style="color:rgb(163, 52, 204);">{{searchword}}</span>에 대한 검색 결과가 없습니다.</p>
            </div>        
        </infinite-loading>  
    </div>
</template>

<script>
import constants from '@/lib/constants'
import InfiniteLoading from 'vue-infinite-loading'
import {instance, instanceWithAuth} from '@/api/index'

export default {
        components:{
            InfiniteLoading
        },
        created (){
            if(this.$store.state.searchword!=''){
                this.activetab = 1
                this.value = this.$store.state.searchword
                this.dosearch()
                this.$store.state.searchword = ''
            }
        },
        computed : {
        },
        methods:{
            tabChange(tabNum) {
                this.activetab = tabNum;
                this.page =0;
                this.lists = []
                this.infiniteId = tabNum;
                // //console.log('탭 변경:'+this.page)
            },
            dosearch(){
                this.searchword = this.value
                this.page = 0;
                this.lists = []
                this.infiniteId += 1
               
            },
            goDetail(pid){
               this.$router.push('detail/'+pid);
            },
            infiniteHandler($state){
                // //console.log($state)
                var vm = this
                instance
                .get('post/search',{
                    params : {
                        page : this.page,
                        type : this.activetab,
                        value : this.value
                    }
                }).then(({data})=>{
                    // //console.log(data);
                    setTimeout (() =>{
                        //console.log(data)
                        this.datasize = data.data
                        if(data.object.length){
                            this.page +=1;
                            this.lists = this.lists.concat(data.object)
                           
                            $state.loaded()
                        }else {
                            $state.complete()
                        }
                    },1000)
                })
            },
            getTotal(){
            },
        },
        data: () => {
            return {
                lists:[],
                activetab: 2,
                value:"",
                page:0,
                infiniteId: 0,
                tags : '',
                taglist : [],
                searchword :'',
                datasize:0
            }
        }
}
</script>

<style scoped>

.msg2{
    color:#7e7d7d;
    font-size:13px;
}
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
input:focus {outline:none;}
.container {  
    width: 100%;
    margin: 40px auto;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 0.9em;
    color: #888;
}
.inputbox{
    margin:10px auto;
    width: 65%;
    height:100px;
}
.inputValue{
    text-align: left;
    font-size:30px;
    width:100%;
}

/* Style the tabs */
.tabs {
    overflow: hidden;
    width:65%;
    margin:0px auto;
    border-bottom: 1px solid rgb(170, 170, 170);
}

.tabs ul {
    list-style-type: none;
    margin-left: 20px;
}

.tabs a{
    float: left;
    cursor: pointer;
    padding: 12px 24px;
    transition: background-color 0.2s;
    border-right: none;
    border-radius: 10px 10px 0 0;
    font-weight: bold;
    color: #484848;
}
/* Change background color of tabs on hover */


/* Styling for active tab */
.tabs a.active {
    color:rgb(163, 52, 204);
    border-bottom:2px solid rgb(163, 52, 204);
    cursor: default;
}
/* .content{
    border-top: 1px solid rgb(170, 170, 170);
} */
/* Style the tab content */
.tabcontent {
    min-height: 180px;
    padding: 30px;
    width:65%;
    margin:0 auto;

}
  ol {
    counter-reset: li; /* Initiate a counter */
    list-style: none; /* Remove default numbering */
    *list-style: decimal; /* Keep using default numbering for IE6/7 */
    font: 15px 'trebuchet MS', 'lucida sans';
    padding: 0;
    margin-bottom: 4em;
    text-shadow: 0 1px 0 rgba(255,255,255,.5);
  }

  ol ol {
    margin: 0 0 0 2em; /* Add some left margin for inner lists */
  }

   .rectangle-list li{
    position: relative;
    display: block;
    padding: 10px;
    margin:10px auto;
    /* background: #333; */
    /* color: white; */
    text-decoration: none;
    transition: all .3s ease-out;
    border-bottom:1px solid rgb(170, 170, 170);

  }

  .rectangle-list li:hover {
    background: rgb(163, 52, 204);
  }
.searchlists{
    height:200px;
    overflow:hidden;
}
.searchcontents{
    height:40px;
    font-size:14px;
    overflow:hidden;
}
</style>