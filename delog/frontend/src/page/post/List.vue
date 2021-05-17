<template>
    <div class="detail" id="list" >
        <banner />
        <main-notice/>
        <recent-ten/>
        <top-writer-user/>
        <hash-chart/>
        <top-hit-post/>
        <footer style="min-height:180px; background-color:rgb(51,51,51); color:lightgray;">
            <div>
                <div style="margin:0 auto; width:40%; height:180px; text-align:center; margin-top:60px; padding-top:30px; ">
                    <p style="font-size:24px; ">De-log</p>
                    <p style="font-size:12px; color:darkgray; ">김건우 &#x00A9;금송아지</p>
                    <p style="font-size:10px;">&#x1F4E7;hd7107@naver.com<br>&#x1F4DE;010-4054-7199</p>
       
                </div>
            </div>
        </footer>
     
    </div>
</template>
<script>
    // import '../../assets/css/post.scss'
    import InfiniteLoading from 'vue-infinite-loading'
    import axios from 'axios'
    import Banner from '@/components/Banner'
    import RecentTen from '@/page/post/main/RecentTen.vue'
    import TopHitPost from '@/page/post/main/TopHitPost.vue'
    import TopWriterUser from '@/page/post/main/TopWriterUser.vue'
    import HashChart from '@/page/post/main/HashChart.vue'
    import MainNotice from '@/page/admin/MainNotice.vue'
    import {instance, instanceWithAuth} from '@/api/index'

    export default {
        name:"Post",
        components:{
            Banner,
            MainNotice,
            InfiniteLoading,
            RecentTen,
            // TopfollowUser,
            TopHitPost,
            TopWriterUser,
            HashChart
        },
        created() {
            this.isLoaded = true;
        },
        methods: {
            goDetail(pid){
               this.$router.push('post/detail/'+pid)
            },
        },
        data: () => {
            return {
                limit:0,
                lists:[],
                 pid:"",
                 writer:"",
                title:"",
                contents:"",
                screen : ["/@/assets/git.jpg", "/@/assets/java.jpg", "/@/assets/python.jpg", "/@/assets/spring.jpg", "/@/assets/sql.jpg", "/@/assets/vue.jpg"],

            }
        }
    }
</script>

<style scoped>

.detail{
    width: 100%;
   height:100%;
}
#post_title{
    font-size: 20px;
}
#list{
    display: block;
}

body{
    margin: 0;
    padding: 0;
    min-height: 100vh;
    background: #333;
    /* display: flex; */
    justify-content: center;
    align-items: center;
    font-family: consolas;
}

.container{
    
    /* width: 1000px; */
    position: relative;
    display: flex;
    margin-bottom:100px;
}

.container .card{
    height : 250px;
    display: inline-block;
    margin: 25px;
    margin-bottom: 40px;
    /* margin-right : 50px; */
    position: relative;
    cursor: pointer;
}

.container .card .face{
    /* width : 300px; */
    height: 100%;
    transition: 0.5s;
}

.container .card .face.face1{
    background-image: url('/@/assets/git.jpg');
    position: relative;
     background: #333; 
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1;
    transform: translateY(60px);
}

.container .card:hover .face.face1{
    background: #43b581;
    transform: translateY(0);
}

.container .card .face.face1 .content{
    opacity: 0.2;
    transition: 0.5s;
}

.container .card:hover .face.face1 .content{
    opacity: 1;
}

.container .card .face.face1 .content img{
    max-width: 100px;
}

.container .card .face.face1 .content h3{
    margin: 10px 0 0;
    padding: 0;
    color: #fff;
    text-align: center;
    font-size: 1.5em;
}

.container .card .face.face2{
    height : 50%;
    position: relative;
    /* background: #fff; */
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    box-sizing: border-box;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.8);
    transform: translateY(-70px);
}

.container .card:hover .face.face2{
    transform: translateY(0);
}

.container .card .face.face2 .content p{
    margin: 0;
    padding: 0;
}

.container .card .face.face2 .content a{
    margin: 15px 0 0;
    display:  inline-block;
    text-decoration: none;
    font-weight: 900;
    color: #333;
    padding: 5px;
    border: 1px solid #333;
}

.container .card .face.face2 .content a:hover{
    background: #333;
    color: #fff;
}

</style>