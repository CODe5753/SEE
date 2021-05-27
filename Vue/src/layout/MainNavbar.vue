<template>
  <navbar
    position="fixed"
    :type="type"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"    
  >
    <template >
      <router-link class="navbar-brand" to="/">
        <img v-if="type=='white'" class="" src="img/black-logo-text.png" style="width:9vh;" alt="" />
        <img v-else class="" src="img/white-logo-text.png" style="width:9vh;" alt="" />
      </router-link>
    </template>
    <template slot="navbar-menu">
      <li class="nav-item">    
        <router-link to="/notice">
          <a class="nav-link" href="/"><span style="font-size:17px;">공지사항</span></a>
        </router-link>
      </li>   
      <li class="nav-item">    
        <router-link to="/news">
          <a class="nav-link" href="/"><span style="font-size:17px;">부동산뉴스</span></a>
        </router-link>
      </li>   
      <li class="nav-item">    
        <router-link to="/qna">
          <a class="nav-link" href="/"><span style="font-size:17px;">커뮤니티</span></a>
        </router-link>
      </li>   
      <li class="nav-item">    
        <router-link to="/map">
          <a class="nav-link"><span style="font-size:17px;">지도</span></a>
        </router-link>
      </li>   
      <li class="nav-item">    
        <router-link to="/statistics">
          <a class="nav-link"><span style="font-size:17px;">통계</span></a>
        </router-link>
      </li>   
      <!-- <button @click="tokentest">토큰테스트</button> -->

      <!-- <drop-down
        tag="li"
        title="Components"
        icon="now-ui-icons design_app"
        class="nav-item"
      >
        <nav-link to="/">
          <i class="now-ui-icons business_chart-pie-36"></i> All components
        </nav-link>
        <a
          href="https://demos.creative-tim.com/vue-now-ui-kit/documentation"
          target="_blank"
          class="dropdown-item"
        >
          <i class="now-ui-icons design_bullet-list-67"></i> Documentation
        </a>        
        <nav-link to="/document">
          <i class="now-ui-icons design_bullet-list-67"></i> ExamplePage
        </nav-link>
      </drop-down>
      <drop-down
              tag="li"
              title="Examples"
              icon="now-ui-icons design_image"
              class="nav-item"
      >
        <nav-link to="/landing">
          <i class="now-ui-icons education_paper"></i> Landing
        </nav-link>
        <nav-link to="/profile">
          <i class="now-ui-icons users_single-02"></i> Profile
        </nav-link>
      </drop-down> -->
      <li class="nav-item">    
          <a class="nav-link"><span style="font-size:17px;">|</span></a>
      </li> 
      <li class="nav-item" v-show="!userinfo">
        <!-- <a
          class="nav-link btn btn-neutral"
          href="#"
          target="_blank"
        > -->
        <router-link to="/login" class="nav-link btn btn-neutral">
          <i class="now-ui-icons users_circle-08"></i>
          <p>로그인</p>
        </router-link>
        <!-- </a> -->
      </li>      
      <li class="nav-item" v-show="!userinfo">
        <router-link to="/regist" class="nav-link btn btn-info">
          <p>회원가입</p>
        </router-link>
      </li>      
      <li class="nav-item" v-if="userinfo">    
        <router-link to="/myinfo">
          <a class="nav-link"><span style="font-size:17px;">내정보</span></a>
        </router-link>
      </li> 
      <li class="nav-item" v-if="userinfo">    
          <a class="nav-link" @click="logout"><span style="font-size:17px;">로그아웃</span></a>
      </li>     
      <!-- <li class="nav-item" v-if="userinfo" @click="logout">
        <a
          class="nav-link btn btn-default"
          target="_blank"
        >
          <p>로그아웃</p>
        </a>
      </li> -->
      
    </template>
  </navbar>
</template>

<script>
import {  Navbar,  Button } from '@/components';
import { Popover } from 'element-ui';
import { mapGetters } from "vuex";
// import NavLink from '../components/Navbar/NavLink.vue';
// import NavLink from '../components/Navbar/NavLink.vue';
export default {
  name: 'main-navbar',
  props: {
    transparent: Boolean,
    colorOnScroll: Number,
    type: String
  },
  components: {
    // DropDown,
    Navbar,
    // NavLink,
    [Button.name]: Button,
    [Popover.name]: Popover
  },
  computed:{
    ...mapGetters(["userinfo"])
  },
  methods: {
    logout(){
      this.$store.dispatch("logout").then(()=>this.$router.push("/").catch(()=>{}));
    },
    tokentest(){
      console.log("tokentest called");
      this.$store.dispatch("checkToken",this);
      console.log(this.username);
      if(!this.username){
        console.log('fail');
        this.$router.push('/login').catch(()=>{});
      }else{
        console.log(this.username+' 로그인성공');
      }
    }
  },
};
</script>

<style scoped></style>
