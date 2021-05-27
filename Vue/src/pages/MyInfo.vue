<template>
<div>
  <div class="page-header clear-filter " filter-color="black">
    <parallax
        class="page-header-image"
        style="background-image:url('img/house_header.jpg');"
      >
    </parallax>
    <!-- <div class="container">
      <br>
      <h1>내 정보</h1>
    </div> -->
    <div class="container">
        <div class="photo-container">
          <img src="/img/default-avatar.png" style="width:150px" alt="" class="rounded-circle img-raised"/>
        </div>
        <h3 class="title">{{userinfo.name}}</h3>
        <p class="category">{{userinfo.email}}</p>
        <p class="category">{{userinfo.role=="[ROLE_USER]"?"일반 회원":"관리자"}}</p>
        <div class="content form-inline">
          <div class="social-description col-md-6">
            <h2>{{qnas.length}}</h2>
            <p>게시물</p>
          </div>
          <div class="social-description col-md-6">
            <h2>{{apts.length}}</h2>
            <p>북마크</p>
          </div>
        </div>
      </div>
  </div>
      <div class="section" style="height:100%;">
          <div class="container">
          <div v-if="apts.length">    
              <h4 class="title" style="color:black">나의 북마크 목록</h4>  
              <b-table id="my-table"                
                :per-page="perPage" 
                :current-page="currentPage"    
                :items="getAptFields()"
                :fields='aptfields'
                :fixed="true"
                hover
                @row-clicked="myRowClickHandler_apts"
                >
              </b-table>
          </div>
          <b-pagination
            v-model="currentPage"
            :total-rows="apts.length"
            :per-page="perPage"
            align="center"
            aria-controls="my-table"
          ></b-pagination>
        </div>
      </div>
      <div class="section" style="height:100%;">
          <div class="container">
          <div>    
              <h4 class="title" style="color:black">내가 작성한 게시글</h4>  
              <div v-if="!qnas.length">
                <h5 class="text-center text-danger">게시글이 존재하지 않습니다.</h5>
              </div>
              <div v-else>
                <b-table id="my-table"                
                  :per-page="perPage" 
                  :current-page="currentPage"    
                  :items="getFields()"
                  :fields='fields'
                  :fixed="true"
                  hover
                  @row-clicked="myRowClickHandler"
                  >
                </b-table>
              </div>
          </div>
          
          <div v-if="qnas.length">
          <b-pagination
            v-model="currentPage"
            :total-rows="rows"
            :per-page="perPage"
            align="center"
            aria-controls="my-table"
          ></b-pagination>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
// import {Card} from '@/components';
import { mapGetters } from "vuex";
export default {
    components:{
        // Card
    },
    data() {
        return {
            perPage :5,
            currentPage : 1,   
            fields:[
                '게시번호',
                {key:'카테고리',sortable:true},
                '제목',
                '내용',
                {key:'게시글종류',sortable:true},
                {key:'작성일자',sortable:true},
                {key:'수정일자',sortable:true},
                {key:'상위게시글',thClass:'d-none',tdClass:'d-none'}
            ],
            aptfields:[
                '시군구',
                '이름',
                {key:'면적',sortable:true},
                {key:'계약일',sortable:true},
                {key:'타입',sortable:true},
                {key:'층',sortable:true},
                {key:'매매(보증금)',sortable:true},
                {key:'건축년도',sortable:true},
                {key:'도로명주소'},
                {key:'번호',thClass:'d-none',tdClass:'d-none'}
            ]  
        }
    },
    computed:{
    ...mapGetters(["qnas","userinfo","apts"]),
    rows(){
    //   console.log(this.qnas.length);
      return this.qnas.length
    }
    
    },
    created() {        
        console.log(this.userinfo.code);
        this.$store.dispatch("getMyQna",this.userinfo.code);
        this.$store.dispatch("getInterestBuildings",this.userinfo.code);
    },
    methods: {
        getAptFields(){
            console.log(this.apts);
            let list = [];
            this.apts.forEach(el => {
                list.push({
                  '시군구':el.sigungu,
                '이름':el.name,
                '면적':el.area,
                '계약일':el.contract_date,
                '타입':el.contract_type,
                '층':el.floor,
                '매매(보증금)':el.dealamount,
                '건축년도':el.buildyear,
                '도로명주소':el.roadname,
                '번호':el.id
                })
            });
            return list;
        },
        getFields(){
            let list = [];
            this.qnas.forEach(el => {
                let content = el.content
                content = content.length>8?content.substr(0,8)+'...':content
                list.push({
                    '게시번호':el.id,
                    '카테고리':el.type,
                    '제목':el.title,
                    '내용':content,
                    // '게시글종류':el.board_id,
                    '게시글종류':el.board_id==0?'질문글':'답글',
                    '상위게시글':el.board_id,
                    // '멤버코드':el.member_code,
                    '작성일자':el.create_date,
                    '수정일자':el.update_date
                })
            });
            return list;
        },
        myRowClickHandler(record,index){
          console.log('Click handler : ');
          console.log(record);
          if(record.상위게시글==0){//이 글이 질문글이라면 
            this.$router.push('qna/view/'+record.게시번호);
          }else{
            this.$router.push('qna/view/'+record.상위게시글);
          }
          // window.open(record.링크);///새탭에서 열기
        },
        myRowClickHandler_apts(record,index){
          console.log('Apts Click handler : ');
          console.log(record);
          this.$router.push('/buildingdetail/'+record.번호);
          // window.open(record.링크);///새탭에서 열기
        },
      
    },
}
</script>
<style scoped>
.page-header{
  min-height: 15vw;
}

</style>