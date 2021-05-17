<template>

    <div class="hashchart" >
        <span class="titleDoHyeon" style="font-size:2.5rem;">T R E N D</span>
        <span class="titleDoHyeon" style="font-size:2.5rem; margin-left:40px;">C H A R T</span>
        <span class="titleDoHyeon" style="font-size:2.5rem;"></span>
        <br>
        <span id="decs" style="color:black;">한달동안 가장 많이 검색된 해쉬태그를 확인해보세요!</span>
        <div class="chart" v-scrollanimation>
         <iframe width="685" height="411" seamless frameborder="0" scrolling="no" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vQpdPlL4kLKmjQiqSkm5rjEJ-R8nzgzcifYN-i4tQJDYJC9s3JixXUyMMzbBcvLibdUkB5ZQHL5VuqE/pubchart?oid=1521556072&amp;format=interactive"></iframe>
        </div>    
    </div>
</template>

<script>
import Vue from 'vue'
import ScrollAnimation from '@/page/post/main/directive/scrollanimation'

Vue.directive('scrollanimation',ScrollAnimation)
export default {
    data() {
        return  {
            BestHashTag : '',
        }
    }
}
</script>

<style scoped>
.hashchart{
    text-align: center;
    width:100%;
    margin:0 auto;
    /* margin-top:50px; */
    background-color: #bac5d159;
    padding:40px;
    transition: all .5s;
}
.hashchart:hover .chart{
    opacity: 1;
}
.chart{
    margin-top:40px;
    margin-bottom: 40px;
    opacity: 0.8;
}
.v-enter-active, .v-leave-active{
    transition: all .17s;
}
.v-enter, .v-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

.before-enter {
    opacity: 0;
    transform: translateY(100px);
    transition: all 2s ease-out;
}
.enter{
    opacity:1;
    transform: translateY(0px);
}
</style>