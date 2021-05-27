import Vue from 'vue';
import Router from 'vue-router';
import Index from './pages/Index.vue';
import Landing from './pages/Landing.vue';
import Login from './pages/Login.vue';
import Profile from './pages/Profile.vue';
import Document from './pages/Document.vue';
import MapTest from './pages/Map.vue';
import Qna from './pages/Qna.vue';
import Regist from './pages/Regist.vue';
import Notice from './pages/Notice.vue';
import MyInfo from './pages/MyInfo.vue';
import News from './pages/News.vue';
import BuildingDetail from './pages/BuildingDetail.vue';
import Admin from './pages/Admin.vue';
import Statistics from './pages/Statistics.vue';
import MainNavbar from './layout/MainNavbar.vue';
import MainFooter from './layout/MainFooter.vue';

Vue.use(Router);

export default new Router({
  linkExactActiveClass: 'active',
  mode:'history',
  routes: [
    {
      path: '/',
      name: 'index',
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/admin',
      name: 'admin',
      components: { default: Admin, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }
      },
      children:[
        {
          path:"",
          name:"memberlist",
          component:()=>import("@/components/adminpage/MemberList.vue"),
        }
      ]
    },
    {
      path: '/statistics',
      name: 'statistics',
      components: { default: Statistics, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }
      },
      children:[
        {
          path:"",
          name:"statisticsview",
          component:()=>import("@/components/statistics/StatisticsView.vue"),
        }
      ]
    },
    {
      path: '/news',
      name: 'news',
      components: { default: News, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }
      },
      children: [
        {
          path: "",
          name: "newslist",
          component: ()=>import("@/components/news/NewsList.vue"),
        },
      ]
    },
    {
      path: '/myinfo',
      name: 'myinfo',
      components: { default: MyInfo, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/regist',
      name: 'regist',
      components: { default: Regist, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }      
      }
    },
    {
      path: "/notice",
      name: "Notice",
      components: { default: Notice, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }      
      },
      children: [
        {
          path: "",
          name: "notice-list",
          component: ()=>import("@/components/notice/NoticeList.vue"),
        },
        // {
        //   path: "minilist",
        //   name: "notice-mini-list",
        //   component: ()=>import("@/components/notice/NoticeMiniList.vue"),
        // },
        {
          path: "view",
          name: "notice-view",
          component: () => import("@/components/notice/NoticeView.vue"),
        },
        {
          path: "create",
          name: "notice-create",
          component: () => import("@/components/notice/NoticeCreate.vue"),
        },
        {
          path: "modify/:no",
          name: "notice-modify",
          component: () => import("@/components/notice/NoticeModify.vue"),
        }
      ]
    },
    {
      path: '/qna',
      name: 'qna',
      components: { default: Qna, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 10 },
        footer: { backgroundColor: 'black' }      
      },
      children:[
        {
          path: "",
          name: "qna-list",
          component: ()=>import("@/components/qna/QnaList.vue")
        },
        {
          path: "view/:id",
          name: "qna-view",
          component: ()=>import("@/components/qna/QnaView.vue")
        },
        // {
        //   path: "view/:id",
        //   name: "view-detail",
        //   component: ()=>import("@/components/qna/include/ViewDetail.vue")
        // },
        {
          path: "create",
          name: "qna-create",
          component: ()=>import("@/components/qna/QnaCreate.vue")
        },
        {
          path: "create/:id",
          name: "qna-create-answer",
          component: ()=>import("@/components/qna/QnaCreateAnswer.vue")
        },
        {
          path: "modify/:id",
          name: "qna-modify",
          component: ()=>import("@/components/qna/QnaModify.vue")
        }
      ]
    },
    {
      path: '/map',
      name: 'map',
      components: { default: MapTest, header: MainNavbar},      
      props: {
        header: { colorOnScroll: 0 , type:'white'},
      }
    },
    {
      path: '/document',
      name: 'document',
      components: { default: Document, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }      
      }
    },
    {
      path: '/landing',
      name: 'landing',
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/login',
      name: 'login',
      components: { default: Login, header: MainNavbar,footer:MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/profile',
      name: 'profile',
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: 'black' }
      }
    },
    {
      path: '/buildingdetail/:id',
      name: 'buildingdetail',
      components: { default: BuildingDetail, header: MainNavbar, footer:MainFooter},      
      props: {
        header: { colorOnScroll: 100 , type:'black'},
      }
    },
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
