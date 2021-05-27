<template>
  <div class="page-header clear-filter" filter-color="black">
    <div
      class="page-header-image"
      style="background-image: url('img/bg02.jpg')"
    ></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <card type="login" plain>
            <div slot="header" class="logo-container" style="width:50%">
              <img v-lazy="'img/white-logo.png'" alt="" />
            </div>

            <fg-input
              class="no-border input-lg "
              addon-left-icon="now-ui-icons ui-1_email-85"
              placeholder="이메일"
              v-model="email"
            >
            </fg-input>

            <fg-input
              class="no-border input-lg"
              addon-left-icon="now-ui-icons text_caps-small"
              placeholder="비밀번호"
              type="password"
              v-model="password"
              @keyup.enter="onSubmit"    
            >
            </fg-input>

            <template slot="raw-content">
              <div class="card-footer text-center">
                <a
                  class="btn btn-primary btn-round btn-lg btn-block has-large"       
                  @click="onSubmit"                         
                  >로그인</a
                >
              </div>
              <!-- <div class="pull-left">
                <h6>
                  <a href="#pablo" class="link footer-link">Create Account</a>
                </h6>
              </div>
              <div class="pull-right">
                <h6>
                  <a href="#pablo" class="link footer-link">Need Help?</a>
                </h6>
              </div> -->
            </template>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from '@/components';
// import MainFooter from '@/layout/MainFooter';

export default {
  name: 'login-page',
  bodyClass: 'login-page',
  data() {
    return {
      email:'',
      password:'',
    }
  },
  components: {
    Card,
    // MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput
  },
  methods: {
    onSubmit: function () {
      console.log("로그인 시도 : "+this.email+", "+this.password);
      this.$store.dispatch("login",{
        email:this.email,
        password:this.password
      }).then(()=>{
        // this.$router.push('/')
        this.$router.go(-1);//로그인 요청받은 곳으로 다시 돌아감
      }).catch(()=>{        
        this.email='';
        this.password='';
        this.$router.push('/login').catch(()=>{});
      });
    }
  },
};
</script>
<style></style>
