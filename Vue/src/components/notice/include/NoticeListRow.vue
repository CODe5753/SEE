<template>
  <div>
    <li class="row">
      <router-link :to="`/notice/view?id=${no}`" class="text col-md-6">{{ title }}</router-link>
      <span class="day text-right col-md-6">{{ regdate.substr(0,10) }}</span>
    </li>
  </div>
</template>

<script>
// import ViewDetail from "@/components/qna/include/ViewDetail.vue";
export default {
  name: "listrow",
  components:{
  },
  props: {
    no: Number,
    title: String,
    regdate : String,
  },  
  data() {
    return {
    }
  },
  methods: {
  }
};
</script>