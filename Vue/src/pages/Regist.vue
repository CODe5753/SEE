<template>
  <div class="page-header clear-filter" filter-color="black">
    <div
      class="page-header-image"
      style="background-image: url('img/bg03.jpg')"
    ></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="">
            <div slot="header" class="logo-container text-center" style="text-align:center">
              <img v-lazy="'img/black-logo.png'" alt="" style="width:30%;"/>
              <h2 class="text-black">회원가입</h2>
            </div>
            <div class="row">
              <fg-input
                class="no-border input-lg col-md-9"
                addon-left-icon="now-ui-icons ui-1_email-85"
                placeholder="이메일"
                v-model="email"
              >
              </fg-input>
              <button class="col-md-2 btn btn-warning btn-round" @click="doCertified" style="padding:0px; margin:0px;height:40px;">인증</button>              
            </div>
            <div class="row" v-if="emailkeybox">
              <fg-input placeholder="이메일 인증 코드 입력" v-model="certemail" class="col-md-6 no-border input-lg"></fg-input>
              <button class="btn btn-round btn-default col-md-5" style="margin:0px; height:40px;" @click="checkcode">코드 인증하기 : {{time}}초</button>
            </div>
            <p v-if="certiflag" class="text-success">이메일 인증되었습니다.</p>
            <fg-input
              class="no-border input-lg"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="비밀번호"
              type="password"
              v-model="password"              
            >
            </fg-input>
            <fg-input
              class="no-border input-lg" 
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="비밀번호 확인"
              type="password"
              v-model="password2"
              @keyup="checkPassword"
            >            
            </fg-input>
            <span v-html="msg"></span>
            <fg-input
              class="no-border input-lg "
              addon-left-icon="now-ui-icons users_circle-08"
              placeholder="이름"
              v-model="name"
            ></fg-input>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a
                  class="btn btn-info btn-round btn-lg btn-block has-large"       
                  @click="join"                         
                  >회원가입</a
                >
              </div>
            </template>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from '@/components';
import http from "@/util/http-common";

export default {
  name: 'login-page',
  bodyClass: 'login-page',
  data() {
    return {
      email:'',
      password:'',
      name:'',
      password2:'',
      msg:'',
      emailkeybox:false,
      time:270,
      certemail:'',
      certiflag:false,
    }
  },
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },   
    methods: {
      checkcode(){
        this.certiflag=true;
        this.emailkeybox=false; 
        clearTimeout(this.timer);
      },
    startTimer(){
      this.timer = setInterval(()=>this.countdown(),1000);
    },
    countdown(){
      if(this.time>=1){
        this.time--;
        console.log(this.time);
      }else{
        this.time=270;
        this.emailkeybox=false;
        clearTimeout(this.timer);
        alert('이메일 인증 시간 초과. 다시 시도해 주세요');
      }
    },
    checkPassword(){
      if(this.password!=this.password2){
        this.msg = "<span class='text-danger'>비밀번호가 다릅니다.</span>";
      }else{
        this.msg = "<span class='text-success'>비밀번호가 일치합니다.</span>"
      }
    },
    doCertified(){//이메일 인증 메서드
      alert('이메일 인증 코드를 보냈습니다.');
      this.startTimer();
      this.emailkeybox=true;
    },
    join: function () {
      console.log(this.userinfo);
      console.log("회원가입 시도 : "+this.email+", "+this.password);
      http.post('/join',{
        email:this.email,
        password:this.password,
        name:this.name,
      }).then((response)=>{
        console.log(response);
        if(!response.data){
          alert("회원가입에 실패했습니다.");
          // this.$router.push('/regist');
        }else{
          alert("회원가입이 완료되었습니다.");
          this.$router.push('/');
        }
      }).catch((err)=>{        
        console.log(err);
      })
    }
  },
};
</script>
<style scoped>
.input-group{
  margin-bottom:0px !important;
}
</style>
