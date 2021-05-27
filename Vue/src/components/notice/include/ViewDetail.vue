<template>
  <div class="container">    
  <div class="card">
    <div class="card-header" style="margin:30px;">
      <h1>{{ notice.title }}</h1>
      <hr>
    </div>
    <div class="form-group card-body">
        <p for="contnet" v-html="notice.content.replace(/\n/g,'</br>')"></p>
        <div style="padding-top: 15px">
          <router-link v-if="checkAdmin()" :to="`/notice/modify/${notice.no}`" class="btn btn-primary">수정</router-link>
          <a href="#" v-if="checkAdmin()" class="btn btn-danger" @click="deleteNotice(notice.no)">삭제</a>
        </div>
        <router-link to="/notice" class="btn btn-info">목록</router-link>
    </div>    
  </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import http from "@/util/http-common";

export default {
  data() {
    return {
      my_role:null,
    }
  },
  computed: {
    ...mapGetters(['notice','userinfo'])
  },
  created() {
    this.$store.dispatch("getNotice", `${this.$route.query.id}`);
    this.my_role=this.userinfo.role;
  },
  methods: {
    checkAdmin(){
      if(this.my_role&&this.my_role.includes('ADMIN')){
        return true;
      }
      return false;
    },
    deleteNotice(no) {
      console.log('삭제 게시글 : '+no);
      if (confirm("해당 글을 삭제 하시겠습니까?")) {
        http.delete(`/notice/remove/${no}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data.length>0) {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$router.push("/notice");
        }).catch((err)=>{
          console.log('삭제실패 : '+err);
        });
      }
    },
  }
};
</script>

<style scoped>

input,
textarea,
.view {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #787878;
  font-size: medium;
}
</style>
