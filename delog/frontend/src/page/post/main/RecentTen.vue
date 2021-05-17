<template>
    <div class="recent">
        <div class="textbox">
        <span class="titleDoHyeon">R E C E N T</span>
        <span class="titleDoHyeon" style="margin-left:40px;">P O S T</span>
        <br>
        <span id="decs">&#x1F388;최근에 올라온 글들을 확인 해보세요</span>
        </div>
        <div class="recent_content">
            <!-- <div class="blog_card" v-on:click="goDetail(list.pid)"
                v-for="(list, index) in lists"
                :key="list.pid">
                <img  :src="userImage[index]" style="width:200px; height:200px; border:2px solid "/>
                <h3 class="doHyeon"  type="button">{{list.title}}</h3>
                <div v-if="list.contents.length > 31" class="blog_card_contents" v-on:click="goDetail(list.pid)" type="button">
                    <span>{{list.contents.substring(0, 30)}}...</span>
                </div>
                <div v-else class="blog_card_contents" v-on:click="goDetail(list.pid)" type="button">
                    <span>{{list.contents}}</span>
                </div>
                <div class="tagset" v-if="list.tags != null ">
                     <span style="display:none">
                    {{taglist = (''+list.tags).substring(0,list.tags.length - 1).split(" ")}}
                    </span> 
                    
                    <span v-if="list.tags.length != 0">
                    <v-btn x-small rounded v-for="tag in list.tags.substring(0,list.tags.length - 1).split(' ')" :key="tag" style="margin:5px; cursor:pointer;"  @click="searchhash(tag)">
                        #{{tag}}
                    </v-btn>
                    </span>
                </div>
                <div class="blog_card_ect">
                    <div class="ect1">
                    {{ list.create_date | moment("from", "now") }}
                    </div>
                    <div class="ect2">
                    <i class="far fa-eye"></i> {{list.hit}}
                    </div>
                </div>
            </div> -->
            <div class="post-card"  v-for="(list, index) in lists" :key="list.post.pid">
                <!-- <div class="img-container" v-on:click="goDetail(list.pid)"   >
                </div> -->
                 <img  :src="userImage[index]" class="img-container" @click="goDetail(list.post.pid)" style="cursor:pointer;"/>
                <div class="post-text" v-on:click="goDetail(list.post.pid)">
                    <div class="post-meta">
                        <i class="fa fa-circle-o"></i>
                        <span class="post-date" style="float:right;"><i class="far fa-clock"></i>{{ list.post.create_date | moment("from", "now") }}</span>
                    </div>
                    <h3 class="post-title" v-if="list.post.title.length > 16" >{{list.post.contents.substring(0, 15)}}...</h3>
                    <h3 class="post-title" v-else>{{list.post.title}}</h3>
                    <p class="post-desc" v-if="list.post.contents.length > 36"   type="button">{{list.post.contents.substring(0, 35)}}...</p>
                    <p class="post-desc" v-else type="button">{{list.post.contents}}</p>
                </div>
                <div style="min-height:30px;">
                    <div class="tagset" v-if="list.post.tags != null ">
                        <!-- <span style="display:none">
                        {{taglist = (''+list.tags).substring(0,list.tags.length - 1).split(" ")}}
                        </span> -->
                        
                        <span v-if="list.post.tags.length != 0">
                            <v-btn x-small rounded v-for="tag in list.post.tags.substring(0,list.post.tags.length - 1).split(' ')" :key="tag" style="margin:5px; cursor:pointer;"  @click="searchhash(tag)">
                                #{{tag}}
                            </v-btn>
                        </span>
                    </div>
                </div>
                <div class="author-container" @click="goBlog(list.user.uid)" style="cursor:pointer;">
                    <span>
                        <div class="profilediv; width:50; height:50;">
                            <img class="profileimg" :src="list.user.profile" style=" border:2px solid black" />
                        </div>
                        <p>by {{list.user.nickname}}</p>
                    </span>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from "axios";
    import Vue from 'vue'
    import Vuex from 'vuex'
    import vueMoment from 'vue-moment'
    import {instance} from '@/api/index'
    import store from '@/store/store';
    Vue.use(vueMoment)
    export default {
        created (){
            this.getFive()
        },
        methods :{
        searchhash(word){
            this.$ga.event('버튼','해시태그',word)
            // //console.log(this.$ga);
            this.$store.commit('hashsearch',word)
            this.$router.push('post/search')
        },
            getFive(){
                //console.log("---최근 작성글 top10---getTen()")
                 instance
                 .get('main/list/order/recent')
                 .then((response)=>{
                     this.lists = response.data.object
                    // if(response.data.object.)
                    // this.urlImage = 
                    // console.log(response)
                    }).catch((error)=>{
                    alert('다시 시도해 주세요!')
              })
            },
            goDetail(pid){
                this.$router.push('post/detail/'+pid)
            },
            goBlog(uid){
                this.$router.push('blog/'+uid)
            }
        },
        data :() => {
            return{
                lists :[],
                tags: '',
                taglist : [],
                userImage:["/image/mainImg/1.jpg","/image/mainImg/2.png",
                "/image/mainImg/3.png","/image/mainImg/4.png",
                "/image/mainImg/5.jpg"],
                url: `${process.env.VUE_APP_API_URL}`,
                urlImage:'',
            }
        }


    }
</script>
<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Lato:100,300,400,400italic,700,700italic,900,900italic");
@import url(https://fonts.googleapis.com/css?family=Kaushan+Script);

$bg-color: #CADAE5;
$link-color: #3A7CCE;
$hover-color: #44BCC5;

*,
*:after,
*:before {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

body {
	background-color: $bg-color;
	font-family: "Lato", Helvetica, sans-serif;
}
.recent{
    margin:30px;
    margin-top:50px;
    min-height: 400px;
}
.recent .textbox{
    margin:0 auto;
    text-align:center;
}
.recent_content{
    margin-bottom:50px;
    margin-top:50px;
}
#decs{
    font-size:13px;
}
.post-card:hover .post-title {
    text-decoration: underline;
}
.post-card {
    display: inline-block;
	background-color: #FDFDFD;
	width: 16em;
	height: 450px;
	box-shadow: 0 1px 32px rgba(0,0,0, 0.12), 0 1px 16px rgba(0,0,0, 0.24);
	margin: 2.5em  0.6em;
    .img-container{
        background-size: cover;
		width: 100%;
		height: 50%;
    }
	.post-meta {
		color: #888;
		font-size: 0.8em;
		letter-spacing: 0.02em;
		line-height: 1.5;
		padding-left: 1px;
		vertical-align: middle;
		a {
			color: $link-color;
			font-size: 1.05em;
			letter-spacing: 0.5px;
			text-decoration: none;
			&:hover {
				color: $hover-color;
				transition: color 400ms ease-in-out;
			}
		}
		.fa-calendar {
			position: relative;
			color: #777;
			top: -1px;
			padding-right: 5px;
		}
	}
	.post-text {
		padding: 11px 18px 0px;
		.post-title {
			font-size: 1rem;
			font-weight: 700;
			letter-spacing: 0.5px;
			margin: 6px 0 9px;
			cursor: pointer;
		}
		.post-desc {
			color: rgba(#000, 0.75);
            margin-bottom: 0px;
			font-size: 0.875rem;
			letter-spacing: 0.2px;
			line-height: 1.3;
			overflow: hidden;
			text-overflow: ellipsis;
			height: 50px;
			width: 100%;
			white-space: prewrap; 
			padding: 3px 2px;
		}
		.fa-circle-o {
			position: relative;
			color: #666;
			font-size: 6px;
			top: -3px;
			padding: 0 5px 0 3.5px;
		}
	}
}

.tagset{
   margin-left: 15px; 
}

.author-container {
	display: block;
	span {
		position: relative;
		width: 45px;
		height: 45px;
		top: 0.3em;
		left: 1.45em;
		border-radius: 100%;
		margin-right: 10px;
		box-sizing: border-box;
		img {
			width: 50px;
			height: 50px;
		}
	}
	p {
		// display: inline;
		position: relative;
		font-size: 0.9em;
		top: -30px;
		left: 140px;
		a {
			color: $link-color;
			text-decoration: none;
			&:hover {
				color: $hover-color;
				transition: color 400ms ease-in-out;
			}
		}
	}
}

.credit {
	position: relative;
	font-family: "Lato", Helvetica, sans-serif;
	font-size: 1.5em;
	font-weight: 300;
	text-align: center;
	bottom: 0.75em;
	.creator-link {
		color: #000;
		font-family: "Kaushan Script", "Lato", Helvetica, sans-serif;
		font-size: 1.05em;
		font-weight: 600;
		letter-spacing: 0.5px;
		text-decoration: none;
		transition: all 300ms linear;
		&:hover {
			color: #FFF;
			text-shadow: 0 1px 3px rgba(#000, 0.6);
			transition: all 350ms linear;
		}
	}
}

.profileimg {
        height: 200px;
        width: 200px;
        border-radius: 50%;
        background-color: blueviolet;
        // margin : 16px auto;
    }
</style>