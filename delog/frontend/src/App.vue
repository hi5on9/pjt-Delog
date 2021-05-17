
<template>
  <v-app id="app">
    <Header :isHeader="isHeader">
    </Header>

    <main>
      <router-view/>
    </main>

  </v-app>
</template>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>  

<script>

import Header from './components/common/Header.vue'
import constants from './lib/constants' 

export default {
  name: 'App',
  components: { 
    Header,
  },
  
  data: function () {
    return {
        drawer: null,
        isHeader: true,
        constants,
    }
  },
  created() {
    // document.cookie = "same-site-cookie=foo; SameSite=Lax";
    // document.cookie = "cross-site-cookie=bar; SameSite=None; Secure";
    // document.cookie = "safeCookie1=foo; SameSite=Lax";
    // document.cookie = "safeCookie2=foo";
    // document.cookie = "crossCookie=bar; SameSite=None; Secure";
    let url = this.$route.name;
    this.checkUrl(url);
  },
  watch: {
    $route (to){
        this.checkUrl(to.name);
    }
  },
  methods : {
    checkUrl(url) { 
        let array = [
            constants.URL_TYPE.USER.LOGIN,
            constants.URL_TYPE.USER.JOIN,
        ];
        let isHeader = true;
        array.map(path => {
            if (url === path)
                isHeader = true;
        })
        this.isHeader = isHeader;
    },
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap');

/* font-family: 'Nanum Myeongjo', serif; */

.titleDoHyeon {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
    font-size: 2.0rem;
}

#app {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
}

.h6 {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
    font-size: 1.05rem;
}

.doHyeon {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
    font-size: 0.875rem;
}

.nanumGothic {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
}

.subtitle {
    font-family:'Georgia', 'Nanum Myeongjo', serif;
    font-size: 0.75rem;
}

.outCenter {
    padding-top: 100px;
    padding-left: 20%;
    padding-right: 20%;
    padding-bottom: 100px;
}

.btn{
    cursor: pointer;
    width:130px;
    /* border:1px solid black; */
    display: inline-block;
    border-radius: 15px;
    margin:10px;
    height:40px;
    font-size:12px;
    line-height: 40px;
    font-weight: bold;
    background-color:#f5f5f5;
    color:black;
    transition:all 0.5s;
    text-align: center;
}


.insertbtn:hover{
    background-color:rgb(163, 52, 204);
    color:white;
}

body{
  padding-top:2em;
}
</style>
