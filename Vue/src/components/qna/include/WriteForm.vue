<template>
  <div class="container" style="margin-top:20px;">
    <card>
      <div class="card-header text-center">
          <h1 class="card-title">게시물
            <template v-if="type == 'create'">등록</template>
            <template v-else >수정</template>
          </h1>
      </div>
      <div class="">
        <!-- <label for="id">글번호</label>
        <input type="text" id="id" name="id" v-model="id" ref="id" readonly /><br /> -->
        <div class="row">
          <div class="col-md-10"></div>
          <select v-if="type != 'createanswer'" v-model="category" class="form-control col-md-2" style="background:white;">
            <option value="매매" selected>매매</option>
            <option value="전세">전세</option>
            <option value="월세">월세</option>
          </select>
        </div>
        <label for="title">제목</label>
        <input v-if="type == 'create'" type="text" id="title" name="title" v-model="title" maxlength="45" @keyup="lengthCheck" ref="title" />
        <input v-else-if="type == 'createanswer'" type="text" id="title" name="title" @keyup="lengthCheck" maxlength="45" v-model="title" ref="title" />
        <input v-else type="text" id="title" name="title" v-model="title" ref="title" readonly /><br />

        <!-- <label for="member_code">작성자</label>
        <input type="text" id="member_code" name="member_code" v-model="member_code" ref="member_code" readonly /><br /> -->

        <label for="content">내용</label>
        <textarea type="text" rows="15" id="content" name="content" v-model="content" ref="content" /><br />
        
        <button class="btn btn-info" v-if="type == 'create'" @click="checkValue">질문등록</button>
        <button class="btn btn-info" v-else-if="type == 'createanswer'" @click="checkValue">답변등록</button>
        <button class="btn btn-info" v-else @click="checkValue">수정</button>
        <button class="btn btn-success" @click="moveList">목록</button>
      </div>
    </card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import http from "@/util/http-common";
import {Card} from '@/components';

export default {
  name: "writeform",
  components:{
    [Card.name]: Card
  },
  props: {
    type: { type: String }
  },
  data() {
    return {
      id:"",
      title: "",
      member_code: "",
      content: "",
      board_id: null,
      category:"매매",
    };
  },
  computed: {
    ...mapGetters(["userinfo"])
  },
  created() {
    if (this.type === "modify") {
      http.get(`/qna/getquestion/${this.$route.params.id}`).then(({ data }) => {
        this.id = data.id;
        this.title = data.title;
        this.member_code = data.member_code;
        this.content = data.content;
      });
    }
    else if(this.type === "createanswer"){
        this.board_id = this.$route.params.id;
    }    
  },
  methods: {
    lengthCheck(){
      if(this.title.length>45){
        alert('제목 최대 길이는 45자 입니다.');
        this.title = this.title.substr(0,45);
      }
    },
    // 입력값 체크하기 - 체크가 성공하면 registQna 호출
    checkValue() {
      // 사용자 입력값 체크하기
      // id, 제목, 작성자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
    //   !this.id && ((msg = "id 입력해주세요"), (err = false), this.$refs.id.focus());
    //   !this.id && ((msg = "임시로 id를 부여합니다."), (err = false), this.id=12);
      if(!this.member_code )this.member_code=this.userinfo.code;//임시 id 부여
      err && !this.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err && !this.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());
      
      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 registQna 호출
      else if(this.type == "create" || this.type=="createanswer"){
          this.registQna();
      }else if(this.type == "modify"){
          this.modifyQna();
      }
       
    },
    registQna() {
      http
        .post("/qna/regist", {
          title: this.title,
          member_code: this.member_code,
          content: this.content,
          board_id: this.board_id,
          type:this.category,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data.length>0) {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.$router.go(-1);
        });
    },
    modifyQna() {
      http
        .put('/qna/update', {
          id: this.id,
          title: this.title,
          member_code: this.member_code,
          board_id:this.board_id,
          content: this.content,
          type:this.category,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data.length>0) {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.go(-1);
        });
    },
    moveList() {
      this.$router.push("/qna");
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
