import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import router from '@/router';
import { getUidFromCookie, getAuthFromCookie, getNicknameFromCookie } from '@/utils/cookies';
import { instance, instanceWithAuth } from '@/api/index';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        uid: getUidFromCookie() || '',
        nickname: getNicknameFromCookie() || '',
        token: getAuthFromCookie() || '',
        login_state: 'NONE',
        role: '',
        islogin: 'false',
        ftype: '1',
        showheart: '1',
        isfollow: false,
        thisfollowers: 0,
        thisfollowings: 0,
        myfollowers: 0,
        myfollowings: 0,
        recipient: 0,
        blogowner: '',
        searchword: '',
        profile: '',
        isKakaoLogin: 0,
        shareTitle: '',
        shareTags: '',
    },
    mutations: {
        setToken: function(state, payload) {
            state.token = payload;
        },
        setNickname(state, nickname) {
            state.nickname = nickname;
        },
        setUid(state, uid) {
            state.uid = uid;
        },
        idCheck: function(state, payload) {
            let loginID = state.uid;
            if (loginID == payload) {
                // //console.log('LOGIN STATE - SAME');
                state.login_state = 'SAME';
            } else if (loginID != payload) {
                // //console.log('LOGIN STATE - DIFF:' + loginID + ',' + payload);
                state.login_state = 'DIFF';
            }
        },
        clearNickname(state) {
            state.nickname = '';
        },
        clearToken(state) {
            state.token = '';
        },
        clearUid(state) {
            state.uid = '';
        },
        isLogin(state) {
            state.uid = state.uid;
            if (state.uid == null) state.islogin = 'false';
            else state.islogin = 'true';
        },
        logout: function(state) {
            if (state.uid) {
                commit('clearNickname');
                commit('clearToken');
                commit('clearUid');
                deleteCookie('nickname');
                deleteCookie('auth');
                deleteCookie('uid');
                location.reload();
            }
        },
        followingtype(state, payload) {
            state.ftype = payload;
        },
        checkfollow(state, uid1, uid2) {
            // //console.log('팔로잉 체크');
            axios
                .get(state.host + '/user/followcheck', {
                    params: {
                        uid1: uid1,
                        uid2: uid2,
                        type: 1,
                    },
                })
                .then((response) => {
                    // //console.log(response.data);
                    // if(response.data.status) {
                    //      state.isfollow = true
                    // }else{
                    //      state.isfollow = false
                    // }
                })
                .catch((error) => {
                    // //console.log(error);
                });
        },
        getowner(state, uid) {
            //console.log('블로그 주인 구하긔');
            instance
                .get('user/search', {
                    params: {
                        uid: uid,
                    },
                })
                .then((res) => {
                    state.blogowner = res.data.object.nickname;
                    //console.log('블로그주인' + state.blogowner);
                    localStorage.setItem('blogowner', res.data.object.nickname);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        hashsearch(state, word) {
            // //console.log('해시태그 클릭');
            state.searchword = word;
        },
    },
    actions: {
        articleUpdate(state, now) {
            // //console.log('업데이트 요청');
            // //console.log(now)
            router.push('/post/update/' + now.params.id);
        },
        updatenotice(state, now) {
            // //console.log('공지 업데이트');
            router.push('/admin/notice/update/' + now.params.nid);
        },
        articleDelete(state, now) {
            // //console.log('게시글 삭제 요청 받음');
            // //console.log(now)
            instanceWithAuth
                .delete('post/', {
                    params: {
                        pid: now.params.id,
                    },
                })
                .then((response) => {
                    alert('삭제 됐습니다!! ');
                    router.push('/');
                })
                .catch((response) => {
                    // //console.log(response);
                });
        },
        deletenotice(state, now) {
            //console.log('공지 삭제 요청 받음');
            instanceWithAuth
                .delete('notice/', {
                    params: {
                        nid: now.params.nid,
                    },
                })
                .then((response) => {
                    alert('삭제 됐습니다!! ');
                    router.push('/');
                })
                .catch((response) => {
                    // //console.log(response);
                });
        },
        commentFocus(state) {
            // //console.log('vuex 코멘트 포커싱')
            document.getElementById('commentbox').focus();
        },
    },
});
