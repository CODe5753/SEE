<template>
  <div>
    <h1 class="underline">공지사항 목록</h1>
    <div class="container">
      <div class="card-header">
        <div class="text-center input-group">
          <input class="form-control" placeholder="게시물을 검색해 보세요" v-model="keyword" @keyup.enter="doSearch"/>
          <div class="input-group-append">
            <button class="input-group-text" @click="doSearch">🔍</button>
          </div>
        </div>
      </div>
      <br>
      <div class="text-right">
        <button class="btn btn-round btn-info" @click="goRegistNotice">공지사항 등록</button>
      </div>
      <div v-if="notices.length" class="card-body text-center">
        <b-table id="my-table" :per-page="perPage" :current-page="currentPage" :items="this.notices"  class="table table-bordered table-condensed">
          <!-- <colgroup>
            <col style="width: 30%" />
            <col style="width: 40%" />
            <col style="width: 30%" />
          </colgroup>
          <thead>
            <tr>
              <th>게시글 번호</th>
              <th>제목</th>
              <th>작성일</th>
            </tr>
          </thead>
          <tbody>
            <list-row
              v-for="(notice, index) in notices"
              :key="index"
              :no="notice.no"
              :title="notice.title"
              :regdate="notice.regdate"      
            />
          </tbody> -->
        </b-table>
      </div>
      <div v-else class="text-center">
          <br><br>
        <h2>공지사항이 없습니다😥</h2>
      </div>
    </div>
     <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      align="center"
      aria-controls="my-table"
    ></b-pagination>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
//import ListRow from "@/components/notice/include/NoticeListRow.vue";

export default {
  name: "noticelist",
  data() {
    return {
      perPage :10,
      currentPage : 1,
      keyword:'',
    }
  },
  // components: {
  //   ListRow,
  // },
  computed: {
    ...mapGetters(["notices"]),
    rows(){
      console.log(this.notices.length);
      return this.notices.length
    }
  },
  created() {
    this.$store.dispatch("getNoticeList")
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
.form-control{
  font-size:1.5em !important;
}
.container{
  min-height: 80vh !important;
}
</style>
