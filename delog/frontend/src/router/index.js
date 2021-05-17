import Vue from 'vue';
import VueRouter from 'vue-router';

import constants from '@/lib/constants';
import store from '@/store/store.js';
// 뷰티파이 import
import Vuetify from 'vuetify';

// Vuetify 사용 설정
Vue.use(Vuetify);
Vue.use(VueRouter);

// 코드 스플리팅 적용
const router = new VueRouter({
    mode: 'history',
    routes: [
        // 로그인/가입
        {
            path: '/user/join',
            name: constants.URL_TYPE.USER.JOIN,
            component: () => import('@/page/user/Join.vue'),
        },
        {
            path: '/user/userinfo',
            name: constants.URL_TYPE.USER.USERINFO,
            component: () => import('@/page/user/Userinfo.vue'),
            meta: { auth: true },
        },
        {
            path: '/user/follows/:uid',
            name: constants.URL_TYPE.USER.FOLLOWS,
            component: () => import('@/page/user/Follows.vue'),
        },
        {
            path: '/user/FindPwd',
            name: constants.URL_TYPE.USER.FINDPWD,
            component: () => import('@/page/user/FindPwd.vue'),
            meta: { auth: true },
        },
        // 포스트
        {
            path: '/',
            name: constants.URL_TYPE.POST.MAIN,
            component: () => import('@/page/post/List.vue'),
        },
        {
            path: '/post/likelist/',
            name: constants.URL_TYPE.POST.LIKELIST,
            component: () => import('@/page/post/LikeList.vue'),
            meta: { auth: true },
        },
        {
            path: '/post/write/',
            name: constants.URL_TYPE.POST.WRITE,
            component: () => import('@/page/post/Write.vue'),
            meta: { auth: true },
        },
        {
            path: '/post/update/:id',
            name: constants.URL_TYPE.POST.UPDATE,
            component: () => import('@/page/post/Update.vue'),
            meta: { auth: true },
        },
        {
            path: '/post/detail/:id',
            name: constants.URL_TYPE.POST.DETAIL,
            component: () => import('@/page/post/Detail.vue'),
        },
        {
            path: '/post/search',
            name: constants.URL_TYPE.POST.SEARCH,
            component: () => import('@/page/post/Search.vue'),
        },
        //블로그
        {
            path: '/blog/:uid',
            name: 'blog',
            component: () => import('@/page/blog/Blog.vue'),
        },
        {
            path: '/blogsetting',
            name: 'blogset',
            component: () => import('@/page/blog/BlogSet.vue'),
            meta: { auth: true },
        },
        {
            path: '/feed',
            name: constants.URL_TYPE.FEED.MY,
            component: () => import('@/page/blog/Feed.vue'),
            meta: { auth: true },
        },
        ///쪽지보내기
        {
            path: '/sendnote',
            name: constants.URL_TYPE.NOTE.SENDNOTE,
            component: () => import('@/page/note/SendNote.vue'),
            meta: { auth: true },
        },
        {
            path: '/note',
            name: constants.URL_TYPE.NOTE.NOTE,
            component: () => import('@/page/note/Note.vue'),
            meta: { auth: true },
        },
        //신고하기
        {
            path: '/report',
            name: constants.URL_TYPE.REPORT.SENDREPORT,
            component: () => import('@/page/report/SendReport.vue'),
            meta: { auth: true },
        },
        {
            path: '/reportlist',
            name: constants.URL_TYPE.REPORT.REPORTLIST,
            component: () => import('@/page/report/ReportList.vue'),
            meta: { auth: true },
        },
        //임시저장
        {
            path: '/temp/templist/',
            name: constants.URL_TYPE.TEMP.LIST,
            component: () => import('@/page/temp/TempList.vue'),
            meta: { auth: true },
        },
        {
            path: '/temp/update/:pid',
            name: constants.URL_TYPE.TEMP.UPDATE,
            component: () => import('@/page/temp/TempUpdate.vue'),
            meta: { auth: true },
        },
        //관리자
        {
            path: '/admin/manage/',
            name: constants.URL_TYPE.ADMIN.MANAGE,
            component: () => import('@/page/admin/Manage.vue'),
            meta: { auth: true },
        },
        {
            path: '/admin/stats/',
            name: constants.URL_TYPE.ADMIN.STATS,
            component: () => import('@/page/admin/Stats.vue'),
            meta: { auth: true },
        },
        {
            path: '/admin/notice',
            name: constants.URL_TYPE.ADMIN.NOTICE,
            component: () => import('@/page/admin/Notice.vue'),
        },
        {
            path: '/admin/notice/write',
            name: constants.URL_TYPE.ADMIN.NOTICEWRITE,
            component: () => import('@/page/admin/NoticeWrite.vue'),
            meta: { auth: true },
        },
        {
            path: '/admin/notice/:nid',
            name: constants.URL_TYPE.ADMIN.NOTCIEDETAIL,
            component: () => import('@/page/admin/NoticeDetail.vue'),
        },
        {
            path: '/admin/notice/update/:nid',
            name: constants.URL_TYPE.ADMIN.NOTICEUPDATE,
            component: () => import('@/page/admin/NoticeUpdate.vue'),
            meta: { auth: true },
        },
        {
            path: '/admin/reportlist',
            name: constants.URL_TYPE.ADMIN.ADMINREPORT,
            component: () => import('@/page/admin/AdminReport.vue'),
            meta: { auth: true },
        },
        {
            path: '*',
            component: () => import('@/page/PageNotFound.vue'),
        },
    ],
});

router.beforeEach((to, from, next) => {
    if (to.meta.auth && store.state.nickname == '') {
        //console.log('인증이 필요합니다');
        next('/');
        return;
    }
    next();
});

export default router;
