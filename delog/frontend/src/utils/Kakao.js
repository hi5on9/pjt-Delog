import { instance } from '@/api/index';
import store from '@/store/store.js';
import {
    getAuthFromCookie,
    getNicknameFromCookie,
    getUidFromCookie,
    saveAuthToCookie,
    saveNicknameToCookie,
    saveUidToCookie,
} from '@/utils/cookies';
import { deleteCookie } from '@/utils/cookies';
import vuex from 'vuex';
let onSuccess = (data) => {
    //console.log(data);
    //console.log('success');
    GetMe(data);
};
let onSuccess2 = (data) => {
    //console.log(data);
    //console.log('success');
    alert('카카오 로그아웃');
    Logout(data);
};
let onFailure = (data) => {
    //console.log(data);
    //console.log('failure');
};

let GetMe = async (authObj) => {
    //console.log(authObj);

    Kakao.API.request({
        url: '/v2/user/me',

        success: async (res) => {
            const kakao_account = res.kakao_account;
            const req_body = {
                id: res.id,
                name: kakao_account.profile.nickname,
                email: kakao_account.email,
                birthday: kakao_account.birthday,
                kakaoAccessToken: authObj.access_token,
                profileIMG: kakao_account.profile.profile_image_url,
            };
            social_login(req_body);
        },
        fail: (error) => {
            LoginFailure();
            //console.log(error);
        },
    });
};

let Logout = () => {
    //console.log('kakao Logout');
    Kakao.Auth.logout((res) => {
        //console.log(res);
        if (!res) return LoginFailure();
        social_logout();
    });
};

let social_login = async (data) => {
    // 기존 DB에 있는지 확인

    await instance
        .post('user/login/kakao', {
            email: data.email,
            nickname: data.name,
            provider: 'kakao',
            snsid: data.id,
            profile: data.profileIMG,
        })
        .then((response) => {
            const token = response.headers['jwt_auth_token'];
            const nickname = response.data.object.nickname;
            const uid = response.data.object.uid;
            store.commit('setToken', token);
            store.commit('setNickname', nickname);
            store.commit('setUid', uid);
            store.state.isKakaoLogin = 1;
            store.state.profile = data.profileIMG;
            saveAuthToCookie(token);
            saveNicknameToCookie(nickname);
            saveUidToCookie(uid);
            location.reload();
        })
        .catch((response) => {
            //console.log(response);
        });
};

let social_logout = async (data) => {
    store.commit('clearNickname');
    store.commit('clearToken');
    store.commit('clearUid');
    deleteCookie('nickname');
    deleteCookie('auth');
    deleteCookie('uid');
    store.state.isKakaoLogin = 0;
    alert('카카오 로그아웃 되었습니다.');
};

export { onSuccess, onFailure, GetMe, Logout, onSuccess2 };
