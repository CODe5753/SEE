<template>
  <div>
    <div id="bg" class="page-header clear-filter" filter-color="black">
      <!-- <div id="foo"></div> -->
      <parallax
        class="page-header-image"
        style="background-image:url('img/header.jpg')"
      >
      </parallax>
      <div class="container">
        <div class="content-center brand">    
          <div v-popover:popover1 >
            <form-group-input class="has-large" 
                              placeholder="지하철, 건물명, 주소로 검색해 보세요" 
                              v-model="keyword"
                              @keyup.enter="search"
                              >
            </form-group-input>      
            <el-popover
              ref="popover1"
              popper-class="popover"
              placement="top"
              width="400px"
              trigger="hover"
            >
              <div class="popover-body text-center">
                검색어 입력후<br>
                엔터를 눌러주세요!
              </div>
            </el-popover>
          </div>
          <div style="transform: translate(0px, 0px); opacity: 1; transition: transform 800ms ease 0s, opacity 800ms ease 0s;">
            <div style="overflow:hidden; height:100px">
              <img src="img/logo-animation-unscreen.gif" 
                    style="
                      max-width:15vw;              
                      margin-top:-2vw;        
                    ">
            </div>
            <h1 class="h1-seo">베타 서비스 오픈!</h1>
            <h2>약 <b>10만개</b> 실거래 데이터 보유!</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="main">
      <div class="section section-images">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="hero-images-container zoom">
                <img src="img/hero-image-iMac.png" alt="" />
              </div>
              <div class="hero-images-container-1 zoom">
                <img src="img/hero-image-iPad.png" alt="" />
              </div>
              <div class="hero-images-container-2 zoom">
                <img src="img/hero-image-iPhone.png" alt="" />
              </div>
            </div>
            <div class="text-center col-md-12" style="margin-top:50px;">
            <h1>이젠 모든 환경에서 사용하세요</h1>
            </div>
          </div>
        </div>
      </div>
    </div>
    <time-line></time-line>
  </div>
</template>
<script>
import { Parallax } from '@/components';
import {FormGroupInput} from '@/components';
import { Popover } from 'element-ui';
// import NoticeMiniList from '@/components/notice/NoticeMiniList.vue';
import TimeLine from './TimeLine.vue';
// import { mapGetters } from "vuex";


export default {
  name: 'index',
  bodyClass: 'index-page',
  mounted() {
    // var f = document.getElementById('foo');
    //   document.addEventListener('click', function(ev){
    //       f.style.left = (ev.clientX-25)+'px';
    //       f.style.top = (ev.clientY-25)+'px';
    // },false);        
    // var observer = new MutationObserver(function(mutations) {
    //     mutations.forEach(function(mutationRecord) {
    //       // console.log(parseInt(f.style.top));
    //       if(parseInt(f.style.top)<200){
    //           console.log(f.style.top);
    //           var bg = document.getElementById("#bg");              
    //       }
    //     });    
    // });    
    // observer.observe(f, { attributes : true, attributeFilter : ['style'] });
  },
  // computed:{
  //   ...mapGetters(["keyword"])
  // },
  data() {
    return {
      keyword:'',
    }
  },
  methods: {
    search(){
      console.log(this.keyword);
      this.$router.push({name:'map',params:{
        'keyword':this.keyword}
      });
    }
  },
  components: {
    FormGroupInput,
    Parallax,
    [Popover.name]: Popover,
    // NoticeMiniList,
    TimeLine
  }
};
</script>
<style scoped>
  .focus{
        -webkit-text-size-adjust: 100%;
    -webkit-tap-highlight-color: rgba(0,0,0,0);
    font-family: 'Lato', sans-serif !important;
    font-size: 14px;
    color: #808080;
    font-weight: normal;
    line-height: 25px;
    text-align: center;
    box-sizing: border-box;
    display: block;
    padding-top: 100px;
    padding-bottom: 100px;
    overflow: hidden;
    background: #FFFFFF;
  }
  .has-large{
    margin-bottom: 10vh !important;
  }
  #foo{
    border-radius:50px;
    width:50px;
    height:50px;
    /* background:rgb(182, 34, 34); */    
	  background: radial-gradient( circle, rgb(208 19 10 / 50%), #793434, #963f3f );
    position:absolute;
    top:0;
    left:0;
    -moz-transition: all 1s; 
    -webkit-transition: all 1s;  
    -ms-transition: all 1s;  
    -o-transition: all 1s;  
    transition: all 1s;  
  }

  /* background: linear-gradient(137deg, #fff6b7, #f77c99, #6578f2, #c2ffd8); 
  background-size: 800% 800%; 
  
  -webkit-animation: AnimationName 16s ease infinite; 
  -moz-animation: AnimationName 16s ease infinite; 
  -o-animation: AnimationName 16s ease infinite; 
  animation: AnimationName 16s ease infinite;  */
  @keyframes gradient {
	0% {
    /* background:linear-gradient(0deg, rgba(219, 10, 10, 0.8), rgba(197, 24, 145, 0.8)) */
		background-position: 0% 50%;
	}
	50% {
    /* background:linear-gradient(0deg, rgba(14, 10, 219, 0.8), rgba(10, 47, 211, 0.8))  */
		background-position: 100% 50%;
	}
	100% {
    /* background:linear-gradient(0deg, rgba(176, 179, 19, 0.8), rgba(14, 11, 10, 0.8)) */
		background-position: 0% 50%;
	}
}
  #bg{
    background:linear-gradient(0deg, rgba(44, 44, 44, 0.8), rgba(14, 11, 10, 0.8));
    /* background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab); */
    background-size: 400% 400%;
    animation: gradient 15s ease infinite;
  }
</style>
