<template>
<div class="dog-container">
  <div class="body"></div>
  <div class="legA"></div>
  <div class="legB"></div>
  <div class="legC"></div>
  <div class="legD"></div>
  <div class="tail"></div>
  <div class="ear"></div>
  <div class="nose"></div>
  <div class="eye"></div>
  <div class="tounge"></div>
     </div>
</template>

<script>
export default {

}
</script>

<style scoped>
body{
  background-color:#7DC387;
  padding:0;
  margin:0;
}
.dog-container{
  background-color:#fad06c;
  width:120px;
  height:70px;
  
  position:absolute;
  top:40%;
  left:40%;
  border-radius:10px 10px 10px 100px;
}
.body{
  background-color:#fad06c;
  width:170px;
  height:90px;
  position:relative;
  top:65px;
  left:80px;
  border-radius:10px 10px 10px 55px;
}
.legA,.legB,.legC,.legD{
  background-color:#fad06c;
  width:19px;
  height:55px;
  position:relative;
}
.legA{
  left:100px;
  top:50px;
  border-radius:0px 0px 10px 10px;
}
.legB{
  left:130px;
  bottom:9px;
   border-radius:0px 0px 10px 10px;
 background-color:#ffb838;
  z-index:-1;
}
.legC{
  left:205px;
  bottom:60px;
  background-color:#ffb838;
  z-index:-1;
  border-radius:0px 0px 10px 10px;
}
.legD{
  left:231px;
  bottom:114px;
  border-radius:0px 0px 10px 10px;
}
.tail{
  width:0;
  height:0;
  border-left:10px solid transparent;
   border-left:9px solid transparent;
  border-bottom:100px solid #fad06c;
  position:relative;
  bottom:320px;
  left:230px;
  transform:rotate(30deg);
  transform-origin:bottom;
  animation:animate 1s infinite;
  border-radius: 7px;
}
@keyframes animate{
  50%{
    transform:rotate(15deg);
  }
}
.ear{
  background-color:#000;
  position:relative;
  width:42px;
  height:90px;
  border-radius:15px 15px 100px 100px;
  left:78px;
  bottom:410px;
  
}
.nose{
  background-color:#000;
  height:12px;
  width:12px;
  border-radius:50%;
  position:relative;
  right:1.5px;
    bottom:499px
}
.eye{
  background-color:#000;
  height:10px;
  width:12px;
  border-radius:50%;
  position:relative;
  bottom:500px;
  left:50px;
}
.tounge{
  background-color:tomato;
  height:20px;
  width:8px;
  border-radius:10px;
  position:relative;
  left:23px;
  bottom:475px;
  transform:rotate(45deg);
  z-index:-1;
  animation:animate1 1s infinite;
}
@keyframes animate1{
  50%{
    transform:rotate(45deg) translateY(2.5px);
  }
}

</style>