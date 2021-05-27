<template>
  <tr>
    <td>{{ no }}</td>
    <td><router-link :to="`/notice/view?id=${no}`">{{ title }}</router-link></td>
    <td>{{ regdate }}</td>
  </tr>
</template>

<script>
// import ViewDetail from "@/components/qna/include/ViewDetail.vue";
export default {
  name: "listrow",
  props: {
    no: Number,
    title: String,
    regdate : String,
  },
};
</script>