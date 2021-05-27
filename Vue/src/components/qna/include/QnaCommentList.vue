<template>
    <div>
      <div v-for="(comment,index) in comments" :key="index" class="card-body">
        <badge>{{comment.member_code}}</badge>
        <!-- <p>{{comment.regdate}}</p> -->
        <span>{{comment.comment}}</span>
      </div>
    </div>
</template>
<script>
import http from "@/util/http-common";
// import {Badge} from '@/components'
export default {
  name: "listcomment",
  components:{
    // [Badge.name]: Badge,
  },
  props: {
    answerid: Number,
  },
  data() {
    return {
        comments:[],
    }
  },
  created() {
    var ref = this;
    http.get('/qna/getcomment/'+this.answerid).then(({ data }) => {
        console.log('정상 응답 완료'+data);
        var arr = [];
            if(data){
            data.forEach(element => {
                arr.push(element);
            });
        }
        ref.comments = arr;          
    }).catch((err)=>{
        console.log('댓글 조회 실패 : '+err);
    });
  },
  methods: {
    
  }
};
</script>