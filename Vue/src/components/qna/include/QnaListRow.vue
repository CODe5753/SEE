<template>
  <router-link :to="`qna/view/${id}`">
    <card class="card-nav-tabs hover" header-classes="card-header-success" style="background-color:white; color:black; width:100%;">
      <div class="text-right">      
        <badge type="primary" v-if="checkNew()">New</badge>
        <badge type="info">{{type}}</badge>
      </div>
      <h5 class="card-title text-left"><b>{{title}}</b></h5>
      <p class="card-text text-left" >{{content}}</p>
      <h6 class="text-left text-muted">{{create_date}}</h6>
    </card>
  </router-link>
</template>
<script>
import {Card, Badge} from '@/components'

// import ViewDetail from "@/components/qna/include/ViewDetail.vue";
export default {
  name: "listrow",
  components:{
    Card,
    [Badge.name]: Badge
  },
  props: {
    id: Number,
    title: String,
    member_code: Number,
    content: String,
    create_date: String,
    type: String,
  },
  data() {
    return {
      isNew:'',
    }
  },
  methods: {
    checkNew(){//최신글 표시
      var date = new Date();
      var cd = new Date(this.create_date);
      var dateDiff = Math.ceil((date.getTime()-cd.getTime())/(1000*3600*24));      
      if (dateDiff==1)
        return true;
      return false;
    },
    goDetail(){
        console.log("클릭한 게시물 ID : "+this.id);
    }
  }
};
</script>
<style scoped>
.card-nav-tabs{
  width:100%;
}
.card-title{
  text-overflow: ellipsis;
  overflow: hidden;
  display:-webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.card-text{
  text-overflow: ellipsis;
  overflow: hidden;
  display:-webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
h6{
  text-overflow : ellipsis;
}
</style>