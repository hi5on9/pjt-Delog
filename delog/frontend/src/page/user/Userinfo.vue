<template>
  <div  class="manage outCenter" id="join">
    <div style="margin-top:5px;margin-left: 1%; margin-right: 1%;">
      <p class="titleDoHyeon" style="margin-bottom:30px;  border-bottom: 3px solid #7e7d7d;">회원정보관리</p >
    </div>
<!-- -------------------------------------------이미지 등록 -------------------------------------------------------->
      <p class="h6">현재 프로필 이미지</p>
      <img class="profileimg" :src="userImage" style="width:200px; height:200px; border:2px solid "/>
      <form @submit.prevent="submitImage" class="form">
          <!-- <P class="subtitle" style="font-size:1rem;">프로필 이미지 변경</P> -->

          <div class="btn insertbtn" style="width:150px; margin-left:0px;" @change="onFileChange">
            <label for="photo">프로필 이미지 선택</label>
            <input type="file" id="photo">
          </div>
          <!-- <input type="file" name="photo" id="photo" @change="onFileChange"/> -->
          <br>
          <button class="btn insertbtn" type="submit" style="width:150px; margin-left:0px;">
            <span>프로필 이미지 저장</span>
          </button>
  
      </form>
      <DeskCSS style="float:right"/>
      <br><br><br><br>
<!-- ------------------------------------------------------------------------------------------------------------ -->
<!-- -------------------------------------------닉네임 변경 -------------------------------------------------------->
      <div class="h6">
        닉네임 : <input id = "nickname" name = "nickname" v-model="nickname" type="text" readonly>
      </div>
      <div class="btn insertbtn" v-if="this.nicknameChange === false" v-on:click="nicknameToggleOn" style="width:100px; margin-left:0px;">
        <span>닉네임 변경</span>
      </div>
        
      <div v-else-if="this.nicknameChange === true">
        <div class="btn insertbtn" @click="nicknameToggleOff" style="width:70px; margin-left:30px;">
          <span>취소</span>
        </div>
        <div class="btn insertbtn" v-on:click="modify" style="width:70px;">
          <span>저장</span>
        </div>
      </div>
<!-- -------------------------------------------------------------------------------------------------------------- -->
<!-- -------------------------------------------비밀번호 변경 -------------------------------------------------------->
        <br>
        <div class="btn insertbtn" v-on:click="passwordToggleOn" style="width:130px; margin-left:0px;">
          <span>비밀번호 변경</span>
        </div>
          
        <Modal v-if="showModal2" @close="showModal2 = false">
            <h3 slot="header" style="margin-top:20px;">
                <i class="closeModalBtn fas fa-times" @click="showModal2 = false" style="float:right; cursor:pointer;"></i>
                비밀번호 변경
            </h3>
             
            <div slot="body" class="passwordChange" style="margin-top:50px;">
              <div>기존 비밀번호 : <input id = "passwordBefore" name = "passwordBefore" v-model="passwordBefore" type="password"></div><br>
              <div class="item">변경할 비밀번호 : <input id = "passwordAfter1" name = "passwordAfter1" v-model="passwordAfter1" type="password"></div><br>
              <div class="item">비밀번호 확인 : <input id = "passwordAfter2" name = "passwordAfter2" v-model="passwordAfter2" type="password"></div><br>
              <div class="btn insertbtn" @click="pwCheck()" style="width:90px;">
                <span>등 록</span>
              </div>
            </div>
          </Modal>
<!-- --------------------------------------------------------------------------------------------------------------------------- -->
<!----------------------------------------------------- 회원탈퇴---------------------------------------------------------------- -->
          <br>
          <br>
          <div class="btn insertbtn" @click="btnDelete()" style="width:100px; margin-left:0px; color:red">
            <span>탈퇴하기</span>
          </div>
          <Modal v-if="showModal" @close="showModal = false">
              <h3 slot="header" style="margin-top:50px;">
                  정말로 탈퇴 하시겠습니까?
              </h3>
              <div slot="body">
                  <div class="btn insertbtn" @click="userDelete()" style="width:90px;">
                    <span>확인</span>
                  </div>
                  <div class="btn insertbtn" @click="showModal = false" style="width:90px;">
                    <span>취소</span>
                  </div>
              </div>
          </Modal>
  </div>
</template>

<script>
    import DeskCSS from '@/components/DeskCSS'
    import Modal from '@/components/Modal'////
    import Vue from 'vue';
    import Vuex from 'vuex';
    import { instance, instanceWithAuth }from '@/api/index'
    import {deleteCookie} from '@/utils/cookies'
    export default {
        components:{
          Modal,
          DeskCSS
        },
        created() {
          this.getDetail()
          // this.userImage = 
        },
        mounted(){
        },
        computed: {},
        methods: {
            pwCheck() {
              instanceWithAuth
                .post('user/account/pwcheck', {
                  uid : this.$store.state.uid,
                  password : this.passwordBefore
              })
              .then( (response) => {
                if(response.data.object)  {
                  this.pwdChange();
                } else{
                  alert("현재 비밀번호가 일치하지 않습니다.")
                }
              })
              .catch((response) => {
                alert("잠시 후 다시 시도해주세요.")
              })
            },
            pwdChange() {
              if(this.passwordAfter1 == this.passwordAfter2) {
                this.modifyPassword();
                alert("비밀번호 변경 성공");
              }else{
                alert("바꾸려는 비밀번호가 서로 다릅니다.");
              }
            },

            onFileChange(e) { //이미지 첨부했을 때
              var files = e.target.files || e.dataTransfer.files
              if (!files.length) {
                return
              }
              this.createImage(files[0])
            },
            createImage(file) { //이미지 가져오기
                var reader = new FileReader()
                var vm = this
                reader.onload = (e) => {
                vm.userImage = e.target.result
              }
              reader.readAsDataURL(file)
            },
            getDetail() {
            instanceWithAuth
                .get('user/account/userinfo', {})
                .then((response) => {
                    this.uid = response.data.object.uid;
                    this.nickname = response.data.object.nickname;
                    this.profile = response.data.object.profile;
                    if(response.data.object.provider == "local"){
                      this.userImage = response.data.object.profile;
                      this.isKakaoLogin = false;
                    } else{
                      this.userImage = response.data.object.profile;
                      this.isKakaoLogin = true;
                    }
                    this.nicknameTmp = this.nickname;
                })
                .catch((error) => {
                    alert('회원정보 조회 실패' + error);
                });
            },
             modify() {
              if (!this.nickname) {
                alert("이름을 입력해주세요.");
              } else {
                this.modifyNickname();
              }
            },
            modifyNickname() {
              instanceWithAuth
                  .put("user/account", {
                  uid : this.$store.state.uid,
                  type : 1,
                  password:this.password,
                  nickname:this.nickname
                })
                .then((response) => {
                  alert("저장 성공");
                  this.$store.state.nickname = this.nickname;
                  // location.reload();
                })
                .catch((response) => {
                alert("기입 내용을 다시 확인해주십시오!");
                });
            },
            modifyPassword() {
              instanceWithAuth
                  .put("user/account", {
                    uid : this.$store.state.uid,
                    type : 2,
                    password:this.passwordAfter2,
                    nickname:this.nickname
                })
                .then(function(response) {
                  alert("저장 성공");
                })
                .catch(function(response) {
                alert("기입 내용을 다시 확인해주십시오!");
                });
            },

            nicknameToggleOn() {
              this.nicknameChange = !this.nicknameChange;
              document.getElementById("nickname").readOnly = false;
              document.getElementById("nickname").focus();
            },
            nicknameToggleOff() {
              this.nicknameChange = !this.nicknameChange;
              this.nickname = this.nicknameTmp;
              document.getElementById("nickname").readOnly = true;
            },

            passwordToggleOn() {
              this.showModal2 = !this.showModal2
            },
            btnDelete() {
              this.showModal = !this.showModal;
            },                      
            userDelete() {
              instanceWithAuth
                .delete('user/account', {
                  params: {
                        uid : this.$store.state.uid
                    }
                })
                .then((response) => {
                    alert("회원탈퇴 성공!")
                    this.showModal = !this.showModal;
                    this.$store.commit('clearNickname')
                    this.$store.commit('clearToken')
                    this.$store.commit('clearUid')
                    deleteCookie("nickname")
                    deleteCookie("auth")
                    deleteCookie("uid")
                    if(this.$route.path!=='/') 
                        this.$router.push('/');
                })
                .catch((response) => {

                });
            },

            // kakaoLogin(){
            //   //console.log("kakaoLogin");
            //   instance
            //     .post("")
            // },

            async submitImage(){
              var frm = new FormData();
              var photoFile = document.getElementById("photo");
              // //console.log("photoFile : " + photoFile.files);
              frm.append("file", photoFile.files[0]);
              await 
              instanceWithAuth
                .post('file/profile/upload', frm, {
                  headers: {
                    'Content-Type': 'multipart/form-data'
                  }
              })
              .then((response) => {
                alert("프로필 사진이 등록되었습니다.")
                this.$store.state.profile = this.profile;
                location.reload();
                // 응답 처리
              })
              .catch((error) => {
                alert("파일 업로드 에러")
                // 예외 처리
              })
          }
        },
        data: () => {
            return {
                changType:'',
                uid:'',

                // constants,

                showModal :false,
                nicknameChange:false,
                showModal2:false,

                passwordBefore:'',
                passwordAfter1:'',
                passwordAfter2:'',
                isKakaoLogin:false,
                userImage:'',

                type:0,
                nickname:'',
                password:'',
                profiel:'',
                url: `${process.env.VUE_APP_API_URL}`,
                };
                
        }
    }
</script>
<style scoped>
.profileimg {
    height: 100px;
    width: 100px;
    border-radius: 150px;
    background-color: blueviolet;
    margin-top:10px;
    margin-bottom:10px;
}
.passwordChange div input{
  border: #888888 1px solid;
  border-radius: 5px;
}

.item {
  margin-top: 10px;
}

input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
</style>