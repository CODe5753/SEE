<template>
  <div class="border">
    <h3 class="title">공지사항</h3>
    <button v-if="checkAdmin()" class="btn btn-primary" @click="goRegistNotice">공지등록</button>
    <div>
      <input class="form-control" type="text" v-model="keyword" placeholder="공지사항을 검색해보세요">
      <div v-if="notices.length" class="card-body text-center">        
        <b-table id="my-table" 
          :per-page="perPage" 
          :current-page="currentPage"           
          :items="getNotices()"
          :fixed="true"
          :filter="keyword"
          @row-clicked="myRowClickHandler"
          hover
          >
        </b-table>
      </div>      
      <div v-else class="text-center">
          <br><br>
        <h2>공지사항이 없습니다😥</h2>
      </div>
      <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      align="center"
      aria-controls="my-table"
    ></b-pagination>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
// import ListRow from "@/components/notice/include/NoticeListRow.vue";

export default {
  name: "noticelist",
  data() {
    return {
      perPage :10,
      currentPage : 1,
      keyword:'',
      my_member_code:null,
      fields:['번호','제목','작성시각'],
    }
  },
  components: {
    // ListRow,
  },
  computed: {
    ...mapGetters(["notices",'userinfo']),
    rows(){
      console.log(this.notices.length);
      return this.notices.length
    }
  },
  created() {
    this.$store.dispatch("getNoticeList");
    this.my_role=this.userinfo.role;
  },
  methods: {
    myRowClickHandler(record,index){
      console.log(record.번호);
      this.$router.push({
        name:'notice-view',
        query:{
          id:record.번호
        }
      });
    },
    getNotices(){
      let list = [];
      for(var i = this.notices.length-1;i>=0;i--){
        list.push({
          '번호':this.notices[i].no,
          '제목':this.notices[i].title,
          '작성시각':this.notices[i].regdate
        });
      }
      return list;
    },
    checkAdmin(){
      if(this.my_role&&this.my_role.includes('ADMIN')){
        return true;
      }
      return false;
    },
    goRegistNotice() {
      this.$router.push({ name: "notice-create" });
    },
    doSearch(){
      console.log(this.keyword);
      this.$store.dispatch("getNoticeSearched",this.keyword)  
    }
  }
};
</script>
<style>
div,h3,li,ul{
    margin: 0;
    padding: 0;
}
.border{
  margin:5vh 0;
  height : 100%;
  width : 100%;
  padding : 30px;
  border : 1px solid #eeeeee;
  border-radius: 30px;
}
.title{
    text-align: left;
    display: block;
    position: relative;
    padding-bottom: 21px;
    margin-bottom: 20px;
    color: #222222;
    font-size: 22px;
    
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-weight: 700;
}
.title::after{
  content: '';
    display: block;
    position: absolute;
    left: -1px;
    bottom: 0px;
    width: 30px;
    height: 1px;
    background: #999999;
}
ol,ul{
  list-style: none;
}

.board_ul li{
    position: relative;
    text-align: left;
}
.board_ul li .day {
    position: absolute;
    right: 0px;
    top: 0px;
    color: #aaaaaa;
}
.board_ul li .text {
    position: relative;
    display: inline-block;
    color: #666666;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.text{
  text-decoration: none;
}
.text:hover{
  text-decoration: underline;
}

</style>
