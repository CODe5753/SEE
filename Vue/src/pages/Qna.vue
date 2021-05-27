<template>
<div>
  <div class="page-header clear-filter " filter-color="black">
    <parallax
        class="page-header-image"
        style="background-image:url('img/bg04.jpg');"
      >
    </parallax>
    <div class="container">
      <br>
      <h1>커뮤니티</h1>
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
/* .page-header .page-header-image{
} */
/* .content{
  width:100%;
  margin-top: 7% !important;
  position:absolute;
  z-index: 100;
}
.container{
  overflow:hidden;
} */
</style>