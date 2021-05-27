<template>
  <div class="border">
    <h3 class="title">회원 관리</h3>
    <div>
      <input class="form-control" type="text" v-model="keyword" placeholder="회원을 검색해보세요">
      <div v-if="members.length" class="card-body text-center">        
        <b-table id="my-table" 
          :per-page="perPage" 
          :current-page="currentPage"        
          :fields='fields'
          :items="getMembers()"
          :fixed="true"
          :filter="keyword"          
          hover
          >
          <template #cell(관리)="row">
            <b-button size="sm" @click="deleteMember(row.item.이메일)" class="mr-2 btn-danger">
                삭제
            </b-button>
            <!-- <b-button size="sm" @click="row.toggleDetails" class="mr-2 btn-info">
                수정
            </b-button> -->
          </template>
          <!-- <template #row-details="row">
            <b-card>
                <b-row class="mb-2">
                    <b-col sm="3" class="text-sm-right row">
                        <b>이름:</b>
                        <input type="text" :value="row.item.이름">
                        
                    </b-col>                    
                    <b-col>{{ row.item.age }}</b-col>
                </b-row>
                <b-row class="mb-2">
                    <b-col sm="3" class="text-sm-right"><b>Is Active:</b></b-col>
                    <b-col>{{ row.item.isActive }}</b-col>
                </b-row>
                <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
            </b-card>
          </template> -->
        </b-table>
      </div>      
      <div v-else class="text-center"><br><br>
        <h2>회원이 없습니다😥</h2>
      </div>
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        align="center"
        aria-controls="my-table">
      </b-pagination>
    </div>
  </div>
</template>


<script>
import { mapGetters } from "vuex";
import http from '@/util/http-common.js'
export default {
  name: "membermanage",
  data() {
    return {
      perPage :10,
      currentPage : 1,
      keyword:'',
      my_member_code:null,
      fields:['번호','이메일','이름'],
    }
  },
  components: {
    // ListRow,
  },
  computed: {
    ...mapGetters(["members"]),
    rows(){
      console.log(this.members.length);
      return this.members.length
    }
  },
  created() {
    this.$store.dispatch("getMembers");
  },
  methods: {
    myRowClickHandler(record,index){
      console.log(record.번호);      
    },
    getMembers(){
      let list = [];
      for(var i = this.members.length-1;i>=0;i--){
        list.push({
          '번호':this.members[i].code,
          '이메일':this.members[i].email,
          '이름':this.members[i].name
        });
      }
      return list;
    },
    deleteMember(email ){
        http.delete('/admin/member/'+email).then((res)=>{
            console.log(res);
        }).catch((res)=>{
            console.log(res);
        })
    }
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
