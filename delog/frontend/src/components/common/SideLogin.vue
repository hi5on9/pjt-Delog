<template>
    <div>
        <profile/>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.NOTE.NOTE}" style="margin-left:20px;">
            <v-list-item-action>
                <i class="fas fa-envelope"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">쪽지함</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <hr>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.POST.WRITE}" style="margin-left:20px;">
            <v-list-item-action>
                <i class="fas fa-pen"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">글쓰기</v-list-item-title>
            </v-list-item-content>
        </v-list-item> 
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.FEED.MY}" style="margin-left:20px;">
            <v-list-item-action>
                <i class="fas fa-chalkboard-teacher"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">피드</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.POST.LIKELIST}" style="margin-left:20px;">
            <v-list-item-action>
                <i class="fab fa-gratipay"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">나의 스크랩</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <hr>
        <v-list-item link v-bind:to="{name:constants.URL_TYPE.REPORT.REPORTLIST}" style="margin-left:20px;">
            <v-list-item-action>
                <i class="fas fa-angry"></i>
            </v-list-item-action>
            <v-list-item-content>
                <v-list-item-title class="doHyeon">신고내역</v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        
    </div>
</template>

<script>
    import Profile from './Profile.vue'
    import constants from '@/lib/constants'
    import {deleteCookie} from '@/utils/cookies'
    export default {
        data() {
           return {
               constants
           }
        },
        components: { 
            Profile,
        },
        methods: {
            
        }
    }
</script>

<style>

</style>