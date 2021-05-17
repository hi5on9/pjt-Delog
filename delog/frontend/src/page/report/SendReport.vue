<template>
  <div class="note">
        <div>
            <div>
                <select v-model="reason">
                    <option disabled value="">사유를 선택해 주세요</option>
                    <option value="명예훼손/기타권리 침해">명예훼손/기타권리 침해</option>
                    <option value="음란성 게시물">음란성 게시물</option>
                    <option value="욕설/반말/부적절한언어">욕설/반말/부적절한언어</option>
                    <option value="도배성 게시물">도배성 게시물</option>
                </select>

                <p style="color:#7e7d7d;font-size:13px; margin-top:40px;">
                    권리침해 신고는 먼저 침해한 사용자를 신고 후 <br>
                    침해 증빙 서류를 첨부하여 <br>
                    아래 De-log 메일로 보내주시면 처리됩니다.<br>
                     hd7107@naver.com
                </p>
            </div>
            <br>
            <div class="btn reportbtn" v-on:click="checkReport" style="width:90px;">
                <span>신고하기</span>
            </div>
   
        </div>
  </div>
</template>

<script>
import {instanceWithAuth, instance} from '@/api/index'
export default {
    data: () => {
        return {
            reason:''
        };
    },
    methods:{
        sendReport(){
            // console.log(this.$store.state.uid + " " + this.$store.state.recipient);
            instanceWithAuth
            .post("report", {
                reporterUid: this.$store.state.uid,
                reportedUid: this.$store.state.recipient,
                reason: this.reason,
            })
            .then(() => {
                alert("신고완료!");
                this.$router.go(0);
            })
            .catch(() => {
            });
        },

        //
        //

        checkReport(){
            // console.log(this.$store.state.uid  + " " + this.$store.state.recipient);
            instanceWithAuth
            .post("report/check", {
                reporterUid: this.$store.state.uid,
                reportedUid: this.$store.state.recipient,
            })
            .then((response) => {
                if(response.data.data == "success"){
                    this.sendReport()
                } else{
                    alert("이미 신고된 사람입니다.")
                }
            })
            .catch(() => {
                alert("잠시 후 다시 시도해주세요.")
            });
        },

    }
}
</script>

<style scoped>
select {
    appearance: auto;
    border:1px solid gray;
}
.reportbtn{
    background-color:white;
    color:red;
    border:1px solid red;
    transition: all 0.6s;
    
}
.reportbtn:hover{
    background-color: red;
    color:white;
}
</style>