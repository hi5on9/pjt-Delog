<template>
    <div class="note">
        <div>
            <div>
                <textarea v-if="this.$vuetify.theme.dark == true" type="text" v-model="value" v-on:keyup="typing" maxlength="800" rows="5" cols="55" placeholder="메시지를 입력해 주세요." class="darknote notepage"/>
                <textarea v-else type="text" v-model="value" v-on:keyup="typing" rows="5" cols="55" placeholder="메시지를 입력해 주세요." class="whitenote notepage" />
                <div style="text-align:right; margin-right:30px;">
                {{count}}/800
                </div>
            </div>
            <br>
            <div class="btn insertbtn" v-on:click="sendNote" style="width:90px;">
                <span>보내기</span>
            </div>
        </div>
    </div>
</template>

<script>
import {instanceWithAuth, instance} from '@/api/index'
export default {
    data: () => {
        return {
            value:'',
            count:0
        };
    },
    methods:{
        typing(){
            //console.log('count'+this.count)
            this.count = this.value.length
        },
        sendNote(){
            instanceWithAuth
            .post("note", {
                sender: this.$store.state.uid,
                recipient: this.$store.state.recipient,
                contents: this.value
            })
            .then(() => {
                alert("전송완료!")
                this.$router.go(0);
            })
            .catch(() =>  {
            });
        },
    }
}
</script>
<style scoped>
.note{
    border-top:3px double #c3c3ca;
    padding-top:40px;
}
.notepage{
width:80%;
  border-radius:5px; 
  border:1px solid #c3c3ca;
}
.darknote{
    color:white;
}
.whitenote{
    color:black;
}
</style>