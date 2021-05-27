<template>
<div>
  <div class="page-header clear-filter " filter-color="black">
    <parallax
        class="page-header-image"
        style="background-image:url('img/house_header.jpg');"
      >
    </parallax>
    <div class="container">
      <br>
      <h1>공지사항</h1>
    </div>
  </div>
  <div class="content">
    <div class="container">
      <router-view/>
    </div>
  </div>
  </div>
</template>
<style scoped>
.page-header{
  min-height: 15vw;
}
</style>