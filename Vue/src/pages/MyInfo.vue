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
            <h2>0</h2>
            <p>북마크</p>
          </div>
        </div>
      </div>
  </div>
      <div class="section" style="height:100%;">
          <div class="container">
          <div v-if="qnas.length">    
              <h4 class="title" style="color:black">내가 작성한 게시글</h4>  
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
            ]  
        }
    },
    computed:{
    ...mapGetters(["qnas"]),
    ...mapGetters(["userinfo"]),    
    rows(){
    //   console.log(this.qnas.length);
      return this.qnas.length
    }
    
    },
    created() {        
        console.log(this.userinfo.code);
        this.$store.dispatch("getMyQna",this.userinfo.code);
    },
    methods: {
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
      
    },
}
</script>
<style scoped>
.page-header{
  min-height: 15vw;
}

</style>