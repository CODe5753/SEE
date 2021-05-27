<template>
  <div class="border">
    <h3 class="title">부동산 뉴스 : 매물 정보</h3>
    <div>
      <div v-if="news.length" class="card-body text-center">        
        <b-table id="my-table" 
          :per-page="perPage" 
          :current-page="currentPage"           
          :fields="fields"
          :items="news"
          :fixed="true"
          hover
          @row-clicked="myRowClickHandler"
          >
          <!-- <template slot="링크" slot-scope="{item}">
              <b>{{item}}</b>
          </template> -->
        </b-table>
      </div>      
      <div v-else class="text-center">
          <br><br>
        <h2>뉴스가 없습니다😥</h2>
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
import http from "@/util/http-common";
export default {
  name: "newslist",
  data() {
    return {
      perPage :10,
      currentPage : 1,
      news:[],
      fields:[
          {key:'제목'},
          {key:'링크',thClass:'d-none',tdClass:'d-none'},
          {key:'작성일시'}
      ]
    }
  },
  components: {
    // ListRow,
  },
  computed: {
    rows(){
      console.log(this.news.length);
      return this.news.length
    }
  },
  created() {
      var list = [];
      http.get('/crawler/getlist').then(({data})=>{
          console.log(data);
          data.forEach(el=> {
              list.push({
                  '제목':el.title,
                  '링크':el.link,
                  '작성일시':el.pubdate,
              });
          });
          this.news=list;
      });
  },
  methods: {
    myRowClickHandler(record,index){
      console.log('Click handler : ');
      console.log(record.링크);
      window.open(record.링크);///새탭에서 열기
    },
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
