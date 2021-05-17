<template>
  <div id="join2">
    <!-- <div class="wrap"> -->
      <div class="form-wrap2">
        <h1>📝 WELCOME DE-LOG</h1>
        <div>
          <div class="button-wrap">
            <h3>
              닉네임
            </h3>
            <input class="input-field" v-model="nickName" maxlength="10"  placeholder="닉네임을 입력해주세요." id="nickname" type="text" />
            <button id="btn" style="text-align:center" @click="nicknameSameCheck">닉네임 중복 체크
            </button>
            <div v-if="nickName.length >= 10" style="color:red">
              닉네임은 10글자를 넘을 수 없습니다.
            </div>
          </div>
          <div class="button-wrap">
            <h3>
              아이디
            </h3>
            <input class="input-field" v-model="email" id="email" placeholder="이메일 형식으로 입력해주세요." type="text" />
            <button id="btn" @click="emailSameCheck">이메일 중복 체크</button>
            <div v-if="!isUsernameValid && email" style="color:red">
              이메일 형식으로 입력해주세요.
            </div>
  
            <input class="input-field" v-model="emailCheck" id="emailCheck" placeholder="인증번호 입력" type="text" @keyup='goCheck()'/>
            <button id="btn" @click="emailCertify()">인증번호 전송</button>
            <div id="emailOk" style="color:red; ">
              <span>{{emailOk}}</span>
            </div>
          </div>
          <div class="button-wrap">
            <h3>
              비밀번호
            </h3>
            <input class="input-field2" v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력해주세요" />
            <input class="input-field2"
              v-model="passwordConfirm"
              id="password-confirm"
              :type="passwordConfirmType"
              placeholder="비밀번호를 한번 더 입력해주세요"
            /><br>
            <label>
              <input v-model="isTerm" type="checkbox" class="checkbox"/>
              <span>약관에 동의합니다</span>
            </label>
            <button @click="modalSwitch" style="margin-left:100px">약관 보기</button>
          </div>
          
        </div>

        <Modal v-if="showModal" @close="showModal = false">
          <h3 slot="header">
              <br><br>
              <span>De-log 약관</span>
              <i class="closeModalBtn fas fa-times" @click="showModal = false" style="float:right; color:black; cursor:pointer"></i>
              <br><br>
          </h3>
          <div slot="body">
            개인정보보호법에 따라 DE-LOG에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 동의 거부권 및 동의 거부 시 불이익에 관한 사항을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.
          </div>
        </Modal>
        <button id="btn2" @click="join" >
          <span>작성완료</span>
        </button>
      </div>
    <!-- </div> -->
  </div>
</template>
<script>

import constants from '@/lib/constants';
import { mapGetters } from "vuex";
import {instance, instanceWithAuth} from '@/api/index';
import { validateEmail } from '@/utils/validation';
import Modal from '@/components/Modal';
 import {
    getAuthFromCookie,
    getNicknameFromCookie,
    getUidFromCookie,
    saveAuthToCookie,
    saveNicknameToCookie,
    saveUidToCookie,
} from '@/utils/cookies';
    // import Vue from 'vue'
    // import Vuex from 'vuex'

export default {
  components: {
    Modal
  },
  computed: {
    isUsernameValid() {
    return validateEmail(this.email);
    },
  },  
  created() {},
  methods: {
    modalSwitch() {
      this.showModal = !this.showModal;
    },
    emailSameCheck(){
       instance
      .get("user/account/signup/email/check",{
        params : {
          email : this.email
        }
      })
      .then(response => {
        //console.log(response);
        if(response.data.data === "success" && this.isUsernameValid && this.email){
          alert("사용 가능한 이메일입니다.");
          this.emailSameCertifyFlag = true;
        }
        else{
          alert("유효하지 않은 이메일입니다.");
          this.emailSameCertifyFlag = false;
        }
      })
      .catch(response => {
        alert("이메일을 다시 확인해주세요.");
      })
    },
    nicknameSameCheck(){
       instance
      .get("user/account/signup/nickname/check",{
        params : {
          nickname : this.nickName
        }
      })
      .then(response => {
        if(response.data.data === "success"){
          alert("사용 가능한 닉네임입니다.");
          this.nicknameCertifyFlag = true;
        }
        else{
          alert("이미 존재하는 닉네임입니다.");
          this.nicknameCertifyFlag = false;
        }
      })
      .catch(response => {
        alert("다시 시도해주세요.");
      })
    },
    emailCertify(){
      instance
      .get("user/account/email",{
        params : {
          email : this.email
        }
      })
      .then(response => {
        alert("인증번호가 발송되었습니다.");
        this.temp = response.data.object
      })
      .catch(response => {
        alert("잘못된 이메일입니다.");
      })
    },
    goCheck() {
      if(this.temp == this.emailCheck && this.temp !== ""){
        this.emailCertifyFlag = true;
        document.getElementById("emailOk").style.color="green";
        this.emailOk = "이메일 인증에 성공했습니다."
      } else {
        this.emailCertifyFlag = false;
        document.getElementById("emailOk").style.color="red";
        this.emailOk = "이메일 인증이 필요합니다."
      }
    },
    join() {
      if (!this.nickName) {
        alert("닉네임을 입력해주세요.");
      } else if (this.nicknameCertifyFlag == false) {
        alert("닉네임 중복체크를 해주세요.");
      } else if (!this.email) {
        alert("이메일을 입력해주세요.");
      } else if (this.emailSameCertifyFlag == false ) {
        alert("이메일을 다시 확인해주세요..");
      } else if (this.emailCertifyFlag == false) {
        alert("이메일 인증이 필요합니다.");
      } else if (!this.password) {
        alert("비밀번호를 입력해주세요.");
      } else if (!this.passwordConfirm) {
        alert("비밀번호 확인란을 입력해주세요.");
      } else if (this.password != this.passwordConfirm) {
        alert("비밀번호가 서로 다릅니다.");
      } else if (!this.isTerm) {
        alert("약관동의 해야합니다.");
      }  else {
        this.makeAccount();
      }
    },
    makeAccount() {
      instance
        .post("user/account/signup", {
          nickname: this.nickName,
          email: this.email,
          password: this.password,
          provider: null,
          snsid: null,
          profile: null,
        })
        .then(() => {
          alert("회원가입 성공!")
          if(this.$route.path!=='/') 
            this.$router.push('/');
        })
        .catch(() => {
          alert("잠시 후 다시 시도해주세요")
        });
      },
  },
  watch: {},
  data: () => {
    return {
      showModal:false,
      temp : "",
      email: "",
      emailCheck : "",
      emailOk : "이메일 인증이 필요합니다.",
      nickName: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      emailCertifyFlag : false,
      nicknameCertifyFlag : false,
      emailSameCertifyFlag : false,
      show:true,
    };
  }
};
</script>
<style scoped>
#join2{
  width:100%;
  height:1100px;
}
* {
    font-family: sans-serif;
}
h1{
  text-align: center;
  margin-bottom: 60px;
  margin-top:50px;
}
.form-wrap2 {
    width: 600px;
    height: 100%;
    /* position: relative; */
    margin: 4% auto;
    padding: 5px;
    /* overflow: hidden; */
}
.input-field {
  background-color:white;
    width: 60%;
    margin:10px;
    /* padding: 10px 0; */
    /* border: none; */
    border-bottom: 1px solid #999;
    outline: none;
    background: transparent;
}
.input-field2 {
  background-color:white;
    width: 80%;
    margin:10px;
    /* padding: 10px 0; */
    /* border: none; */
    border-bottom: 1px solid #999;
    outline: none;
    background: transparent;
}
#btn {
  float:right;
    width: 35%;
    padding:5px;
    cursor: pointer;
    display: block;
    margin: auto;
    margin-top:5px;
    /* border: 0; */
    outline: none;
    border-radius: 5px;

    background-color: white; 
    color: black; 
    border: 2px solid #4CAF50;
}
#btn:hover {
  background-color: #4CAF50;
  color: white;
}
#btn2 {
    width: 40%;
    padding:3px;
    cursor: pointer;
    display: block;
    margin: auto;
    background-color: white; 
    color: black; 
    border: 2px solid #4CAF50;
    outline: none;
    border-radius: 5px;
}
#btn2:hover {
  background-color: #4CAF50;
  color: white;
}
.emailOK{
  border:1px solid;
}

span {
    text-align: center;
    font-size: 15px;
    bottom: 68px;
}
#login {
    left: 50px;
}
.button-wrap {
    width: 70%;
    margin: 30px auto;
    position: relative;
    /* box-shadow: 0 0 600px 9px #fcae8f; */
}
</style>