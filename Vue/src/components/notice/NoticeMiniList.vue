<template>
  <div class="border" style="height:300px;">
    <router-link to="/notice">
        <h3 class="title" style="padding-top:0px;">공지사항</h3>
    </router-link>
    <div>
      <div v-if="notices.length" class="card-body text-center" style="padding-top:0px;">
        <ul class ="board_ul">
            <li class="list" >
                <list-row
                    v-for="(notice, index) in getFiveNotice()"
                    ::key="index"
                    :no="notice.no"
                    :key="index"
                    :title="notice.title"
                    :regdate="notice.regdate"      
                /></li>
        </ul>
      </div>      
      <div v-else class="text-center">
          <br><br>
        <h2>공지사항이 없습니다😥</h2>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ListRow from "@/components/notice/include/NoticeListRow.vue";

export default {
  name: "noticeminilist",
  data() {
    return {
      perPage :10,
      currentPage : 1,
      keyword:'',
      getFiveNotice(){
          let list = [];
          let cnt = 0;
          for(var i =this.notices.length-1;i>=0;i--){
              list.push(this.notices[i]);
              if(i==this.notices.length-6){
                  break;
              }
          };
          return list;
      },
    }
  },
  components: {
    ListRow,
  },
  computed: {
    ...mapGetters(["notices"]),
    rows(){
      console.log(this.notices.length);
      return this.notices.length
    }
  },
  created() {
    this.$store.dispatch("getNoticeList");
  },
  methods: {
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
<style scoped>

div,h3,li,ul{
    margin: 0;
    padding: 0;
}
.border{
  height : 280px;
  width : 500px;
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
