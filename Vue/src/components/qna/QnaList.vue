<template>
  <div>
    <!-- <h1 class="underline">Q&A 목록</h1> -->
    <div class="container">
      <div class="card-header">
        <div class="text-center input-group">
          <div class="input-group-prepend">
            <select v-model="selected" class="input-group-text">
              <option value="total">전체</option>
              <option value="member_code">작성자</option>
              <option value="title">제목</option>
              <option value="content">내용</option>
            </select>        
          </div>
          <input class="form-control" placeholder="게시물을 검색해 보세요" v-model="keyword" @keyup.enter="doSearch"/>
          <div class="input-group-append">
            <button class="input-group-text" @click="doSearch">🔍</button>
          </div>
        </div>
      </div>
      <div class="text-right">
        <button class="btn btn-success" @click="goRegistQuestion">게시물 등록</button>
      </div>      
      <div class="col-md-12">
      <div class="col-md-4 row float-right" style="margin-bottom:2vh">
          <select name="" id="" class="form-control col-md-6" v-model="sort_radio">
            <option value="1">최신순</option>
            <option value="2">오래된순</option>
          </select>
          <!-- <n-radio v-model="sort_radio" label="3">답변많은순</n-radio> -->
          <select id="" class="form-control col-md-6" v-model="sort_category">
            <option value="전체">전체</option>
            <option value="매매">매매</option>
            <option value="전세">전세</option>
            <option value="월세">월세</option>
          </select>
      </div></div>
      <div v-if="qnas.length" class="card-body text-center">
        <table id="qna-list" style="width:100%">
          <colgroup>
            <col style="width:100%"/>
          </colgroup>
          <!-- <colgroup>
            <col style="width: 10%" />
            <col style="width: 50%" />
            <col style="width: 20%" />
            <col style="width: 20%" />
          </colgroup>
          <thead>
            <tr>
              <th>게시글 번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>답변 수</th>
            </tr>
          </thead> -->
          <tbody>
            <list-row            
              v-for="(qna, index) in orderedQnas"
              :key="index"              
              :id="qna.id"
              :title="qna.title"
              :member_code="qna.member_code"
              :content="qna.content"
              :create_date="qna.create_date"
              :type="qna.type"              
              hover
            />
          </tbody>
        </table>
      </div>
      <div v-else class="text-center">
        <h2>게시글이 없습니다😥</h2>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
// import {Radio} from '@/components'
import ListRow from "@/components/qna/include/QnaListRow.vue";

export default {
  name: "qnalist",
  data() {
    return {
      sort_radio:'1',
      sort_category:'전체',
      selected:'total',
      keyword:'',
    }
  },
  components: {
    ListRow,
  },
  computed: {
    ...mapGetters(["qnas"]),
    orderedQnas:function(){
      let list = [];
      if(this.sort_radio==1)//최신순
        list = _.orderBy(this.qnas,'create_date','desc');
      else
        list = _.orderBy(this.qnas,'create_date','asc');

      //카테고리 정렬 추가
      if(this.sort_category=="전체"){
        return list;
      }else if(this.sort_category=="월세"){
        let newlist = [];
        list.forEach(el => {
          if(el.type=="월세")
            newlist.push(el);
        });
        return newlist;
      }else if(this.sort_category=="매매"){
        let newlist = [];
        list.forEach(el => {
          if(el.type=="매매")
            newlist.push(el);
        });
        return newlist;
      }else if(this.sort_category=="전세"){
        let newlist = [];
        list.forEach(el => {
          if(el.type=="전세")
            newlist.push(el);
        });
        return newlist;
      }
      return list;
    },
  },
  created() {
    this.$store.dispatch("getQnaQuestions").then(()=>{
          console.log(this.$store.state.qnas);
    }).catch((error)=>{
      console.log(error);
    });
  },
  methods: {
    goRegistQuestion() {
      this.$router.push({ name: "qna-create" });
    },
    doSearch(){
      let payload = {
        keyword:this.keyword,
        selected:this.selected
      }
      if(payload.keyword!=''){
        this.$store.dispatch("getQnaSearched",payload);
      }else{
        this.$store.dispatch("getQnaQuestions").then(()=>{
        });
      }
    }
  }
};
</script>
<style scoped>
.btn-check{
  position: absolute;
  clip: rect(0,0,0,0);
  pointer-events: none;
}
.form-control{
  font-size:1.5em !important;
}
.container{
  min-height: 80vh !important;
}
</style>
