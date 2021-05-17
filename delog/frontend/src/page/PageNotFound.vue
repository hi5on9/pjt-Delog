<template>
  <div class="container">
    <div class="notfound">
      <div class="img">
        <i class="fas fa-exclamation-triangle fa-9x"></i>
      </div>
      <span class="doHyeon">요청하신 페이지를 <span>찾을 수 없습니다</span></span>
      <div class="decs">
        주소가 잘못 입력 되었거나, 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br>
        입력하신 주소가 정확한지 다시 한번 확인해 주세요
      <br><br><br> <hr>
      </div>
      <div class="delog">
      <h4 class="doHyeon">De-log</h4>
      </div>
    </div>

   
  </div>
</template>



<style scoped>
.container{
  margin-top:100px;
  
}
.notfound{
  margin:0 auto;
  height:500px;
  width:500px;
  text-align:center;
}
.notfound span{
  font-size:30px;
}
.img{
  width:250px;
  height:250px;
  margin:0 auto;
  padding:30px;
}
.delog{
  margin-top:130px;
}
span span{
  color:blueviolet;
}
.decs{
  font-size:12px;
  color:gray;
}
i{
  color:blueviolet
}
</style>