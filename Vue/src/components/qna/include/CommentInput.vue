<template>
  <div class="input-group row col-12">            
    <input class="col-md-10" v-model="comment_content">            
    <button class="col-md-2 btn" @click="pushcomment">댓글입력</button>
  </div>
</template>
<script>
import http from "@/util/http-common";
// import ViewDetail from "@/components/qna/include/ViewDetail.vue";
export default {
  name: "commentinput",
  props: {
    boardid: Number,
    member_code : Number,
  },
  data() {
    return {
      comment_content:'',
    }
  },
  methods: {
    pushcomment(){
      http.post(`/qna/registcomment`,{
        member_code:this.member_code,
        board_id:this.boardid,
        comment:this.comment_content
      }).then(({ data }) => {
          this.$router.push(-1);
        }).catch((err)=>{
          console.log('댓글 등록 실패 : '+err);
        });
    }
  }
};
</script>