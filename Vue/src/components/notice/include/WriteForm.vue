<template>
  <div class="regist">
    <h1 class="underline"> 공지사항 게시판
      <template v-if="type == 'create'">등록</template>
      <template v-else >수정</template>
    </h1>
    <div class="regist_form">
      <label for="title">제목</label>
      <input v-if="type == 'create'" type="text" id="title" name="title" v-model="title" maxlength="45" @keyup="lengthCheck" ref="title" />
      <input v-else type="text" id="title" name="title" v-model="title" ref="title" /><br />

      <label for="content">내용</label>
      <textarea type="text" rows="15" id="content" name="content" v-model="content" ref="content" /><br />
      
      <button class="btn btn-info" v-if="type == 'create'" @click="checkValue">공지등록</button>
      <button class="btn btn-info" v-else @click="checkValue">수정</button>
      <button class="btn btn-default" @click="moveList">목록</button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "writeform",
  props: {
    type: { type: String }
  },
  data() {
    return {
      no:"",
      title: "",
      content: "",
    };
  },
  created() {
    if (this.type === "modify") {
      http.get(`notice/select/${this.$route.params.no}`).then(({ data }) => {
        this.no = data.no;
        this.title = data.title;
        this.content = data.content;
      });
    }
  },
  methods: {
    lengthCheck(){
      if(this.title.length>45){
        alert('제목 최대 길이는 45자 입니다.');
        this.title = this.title.substr(0,45);
      }
    },
    checkValue() { 

      let err = true;
      let msg = "";
      err && !this.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err && !this.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);

      else if(this.type == "create"){
          this.registNotice();
      }else if(this.type == "modify"){
          this.modifyNotice();
      }
       
    },
    registNotice() {
      http
        .post("notice/regist", {
          title: this.title,
          content: this.content,
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
    modifyNotice() {

      http
        .put('notice/modify', {
          no : this.no,
          title: this.title,
          content: this.content
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
      this.$router.push("/notice");
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
