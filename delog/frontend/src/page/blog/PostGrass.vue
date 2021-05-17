<template>
  <div class="grass">
    <calendar-heatmap 
    :values="dataInfo"
    end-date="2020-12-31"
    :range-color="['#FBF6FC','#F1E1F7', '#E4C5F0', '#CA8BE1', '#BD6EDA', '#B051D3']"
    :max="6"
    tooltip-unit="log"
     />

  </div>
</template>

<script>
import Vue from 'vue'
import Vuex from 'vuex'
import VueCalendarHeatmap from 'vue-calendar-heatmap'
import {instance, instanceWithAuth} from '@/api/index'
import axios from "axios";
Vue.use(VueCalendarHeatmap)
export default {
 data() {
   return {
     dataInfo : [],
     lists : []
   }
 },
 methods : {
    // onclick(){
    //   log('hi')
    //   console.log('DAY:'+event.target.value);
    // },
  },
  created () {
     instanceWithAuth
     .get('post/daily/count',{
          params : {
          uid : this.$route.params.uid,
          }
      })
      .then(({data})=>{
          this.lists = data.object
          for(var i =0;i<this.lists.length;i++){
            this.dataInfo.push({date :this.lists[i].date,count:this.lists[i].count})
          }
      })
   
  }
}
</script>

<style>
.grass{
  height:500px;
}
.vch__wrapper {
  width:110%;
}
</style>