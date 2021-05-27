<template>
  <div class="container">    
    <div class="card">
    <div class="card-header" style="margin:30px;">
      <h1>{{qna.title}}</h1>
      <hr>
    </div>
    <div class="form-group card-body">
      <div class="text-right">
        <p>{{ qna.create_date }}</p>
        <h6>답글 : {{answers.length}}</h6>
      </div>
      <!-- <label for="author">작성자</label>
      <div class="view">{{ qna.member_code }}</div> -->      
      <h5 v-html="qna.content.replace(/\n/g,'</br>')"></h5>      
      <div></div>
      <div class="text-right" style="width:100%">
        <div style="padding-top: 15px" >
          <router-link v-if="qna.member_code == my_member_code" :to="`/qna/modify/${qna.id}`" class="btn btn-primary">수정</router-link>
          <a href="#" v-if="qna.member_code == my_member_code" class="btn btn-danger" @click="deleteQna(qna.id)">삭제</a>
          <router-link  to="/qna" class="btn btn-info">목록</router-link>
          <button @click="goRegistQuestion" class="btn btn-success" v-if="qna.member_code != my_member_code">답변 등록</button>
        </div>        
      </div>
    </div>
    </div>    
    <div v-for="(answer,index) in answers" :key="index" class="card">
      <div class="container">
        <div class="card-body">
          <h4 for="title"><badge type="success">답글</badge>{{answer.name}}<b>{{answer.title}}</b></h4><br>
          <div v-html="answer.content.replace(/\n/g,'</br>')"></div>
          <p class="text-right">{{answer.create_date}}</p>
          <comment-input
            :boardid="answer.id"
            :member_code="my_member_code"            
          ></comment-input>

          <!-- 답글 -->
          <div v-if="answer.member_code == my_member_code" class="text-right">
            <router-link :to="{name:'qna-modify',params:{id:answer.id}}" class="btn btn-primary">수정</router-link>
            <a href="#" class="btn btn-danger" @click="deleteQna(answer.id)">삭제</a>
          </div>
        </div>
        <list-comment
          :answerid = "answer.id"
        />
      </div>
    </div>
    
  </div>
</template>

<script>
import {Badge} from '@/components'
import { mapGetters } from "vuex";
import http from "@/util/http-common";

import ListComment from "@/components/qna/include/QnaCommentList.vue";
import CommentInput from "@/components/qna/include/CommentInput.vue";
export default {
  name: "viewdetail",
  data() {
    return {
      my_member_code:null,
      // comments:[],
      answerid:'',
      comment_content:'',
    }
  },
  computed: {
    ...mapGetters(["qna","answers","userinfo"])
  },
  components:{
    ListComment,CommentInput,
    [Badge.name]: Badge,
  },
  created() {
    /*질문글, 답변글 가져오기*/
    // alert('viewdetail'+`${this.$route.params.id}`);
    this.$store.dispatch("getQnaQuestion", `${this.$route.params.id}`);
    this.$store.dispatch("getQnaAnswers", `${this.$route.params.id}`);
    this.my_member_code=this.userinfo.code;
  },
  methods: {
    deleteQna(id) {
      console.log('deleteQna 실행 삭제 게시글 : '+id);
      if (confirm("해당 글을 삭제 하시겠습니까?")) {
        http.delete(`/qna/remove/${id}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data.length>0) {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$router.go(-2);
        }).catch((err)=>{
          console.log('삭제실패 : '+err);
        });
      }
    },
    goRegistQuestion() {
      this.$router.push(`/qna/create/${this.qna.id}`);
    }
  }
};
</script>

<style scoped>
.regist {
  padding: 10px;
}
.regist_form {
  text-align: left;
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}
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
