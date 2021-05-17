<template>
<div class="stats">
    <p class="titleDoHyeon" style="margin-left:40px;text-align:left;">De-log 사이트 통계</p>
      <div>
          <div v-scrollanimation class="big">
                <p class="titleDoHyeon">주간 방문 통계</p>
                <iframe width="694" height="390" seamless frameborder="0" scrolling="no" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vQpdPlL4kLKmjQiqSkm5rjEJ-R8nzgzcifYN-i4tQJDYJC9s3JixXUyMMzbBcvLibdUkB5ZQHL5VuqE/pubchart?oid=1209823220&amp;format=interactive"></iframe>
          </div>
          <div v-scrollanimation class="one">
            <div class="half">
                <p  class="titleDoHyeon">사용자 방문수</p>
                <iframe width="345" height="214" seamless frameborder="0" scrolling="no" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vQpdPlL4kLKmjQiqSkm5rjEJ-R8nzgzcifYN-i4tQJDYJC9s3JixXUyMMzbBcvLibdUkB5ZQHL5VuqE/pubchart?oid=1243128881&amp;format=interactive"></iframe>                 
            </div>
            <div class="half">
                <p  class="titleDoHyeon">기기별 세션수</p>
                <iframe width="345" height="214" seamless frameborder="0" scrolling="no" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vQpdPlL4kLKmjQiqSkm5rjEJ-R8nzgzcifYN-i4tQJDYJC9s3JixXUyMMzbBcvLibdUkB5ZQHL5VuqE/pubchart?oid=1171240354&amp;format=interactive"></iframe>          
            </div>
          </div>

          <div v-scrollanimation class="big">
              <p  class="titleDoHyeon">시간별 사용자 수</p>
                <iframe width="694" height="390" seamless frameborder="0" scrolling="no" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vQpdPlL4kLKmjQiqSkm5rjEJ-R8nzgzcifYN-i4tQJDYJC9s3JixXUyMMzbBcvLibdUkB5ZQHL5VuqE/pubchart?oid=1511416141&amp;format=interactive"></iframe>
          </div>
      </div>
</div>
</template>

<script>
import axios from "axios";
import Vue from 'vue'
import Vuex from 'vuex'
import ScrollAnimation from '@/page/post/main/directive/scrollanimation'
Vue.directive('scrollanimation',ScrollAnimation)
export default {

}
</script>

<style scoped>
.stats{
    margin:0 auto;
    width:60%;
    margin-top:80px;
    padding-bottom:100px;
    text-align:center;
}
.big{
    border-bottom:1px solid #f5f5f5;
    margin:50px auto;
    padding:50px;
}
.one{
    border-bottom:1px solid #f5f5f5;

}
.half{
    width:30%;
    display: inline-block;
    /* border:1px solid black; */
    margin:8%;
}
.subtitle{
    font-family:'Georgia';
    font-size: 3rem;
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