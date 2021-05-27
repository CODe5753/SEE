import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
import createPersistedState from 'vuex-persistedstate';
import router from '../router';

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    userinfo:null,    
    keyword:'',
    qnas: [],
    qna: {},
    answers: {},
    notices: [],
    notice: {},
    apts: [],
    apt: {},
  },
  getters:{
    qnas:state => state.qnas,//질문글 리스트 반환
    qna:state => state.qna, //특정 질문글 반환
    answers:state => state.answers, //특정 질문글에 대한 응답글들 반환
    userinfo:state => state.userinfo,//유저의 이름 반환    
    keyword:state => state.keyword,//검색어 반환
    notices(state) {
      return state.notices;
    },
    notice(state) {
      return state.notice;
    },
    
    apts(state) {
      return state.apts;
    },
    apt(state) {
      return state.apt;
    },
  },
  mutations: {//Setter개념
    SET_USER_INFO(state,userinfo){//유저의 정보 세팅
      state.userinfo = userinfo;
    },
    SET_QNAS(state,payload){
      state.qnas = payload;
    },
    SET_QNA(state,payload){
      state.qna = payload;
    },
    SET_ANSWERS(state,payload){
      state.answers = payload;
    },
    setNoticeList(state, payload) {
      state.notices = payload;
    },
    setNotice(state, payload) {
      state.notice = payload;
    },
    
    setAptList(state, payload) {
      state.apts = payload;
    },
    setApt(state, payload) {
      state.apt = payload;
    },
  },
  actions: {
    getMembercode(){
      return this._vm.$session.get('userinfo').code;
    },
    getToken(){//session의 토큰 반환
      return this._vm.$session.get('jwt');
    },
    async getMyQna(context,member_code){//QnA의 내가 작성한 목록을 가져옴
      console.log("[actions:getMyQna] called");
      await http
        .get("/qna/getmyqna/"+member_code)
        .then(({data})=>{
          context.commit("SET_QNAS",data);
        })
        .catch((error)=>{
          console.log(error);
        })
    },
    async getQnaQuestions(context){//질문글의 목록을 가져옴
      console.log("[actions:getQnaQuestions] called");
      await http
        .get("/qna/getquestion")
        .then(({data})=>{
          context.commit("SET_QNAS",data);
        })
        .catch((error)=>{
          console.log(error);
        })
    },
    getQnaAnswers(context, board_id) {//해당하는 글의 답변글만 가져옴
      console.log("[actions:getQnaAnswers] called" + board_id);
      http
        .get("/qna/getanswer/" + board_id)
        .then(({ data }) => {
          // console.log("조회된 답글 갯수 : "+data.length);
          context.commit("SET_ANSWERS", data);
        })
        .catch((error) => {
          console.log(error);
        })
    },
    getQnaQuestion(context, board_id) {//특정 질문글만 가져옴 (상세보기)
      console.log("[actions:getQnaQuestion] called : 하나의 질문글만 가져옵니다 : " + board_id);
      http
        .get("/qna/getquestion/" + board_id)
        .then(({ data }) => {
          context.commit("SET_QNA", data);
        })
        .catch((error) => {
          console.log(error);
          // alert("에러 발생 : " + error)
        })
    },
    getQnaSearched(context, payload) {//검색어, 선택자
      let keyword = payload.keyword;
      let selected = payload.selected;
      console.log("[actions:getQnaSearched] called.. 검색어:" + keyword + ", 선택자:" + selected);
      http
        .get("/qna/getquestion/"+keyword+"/"+selected)
        .then(({ data }) => {
          context.commit("SET_QNAS", data);
        })
        .catch((error) => {
          alert("에러 발생 : " + error)
        })      
    },
    logout(){
      this._vm.$session.remove('jwt');
      this._vm.$session.remove('userinfo');
      this._vm.$session.remove('session-id');
      // SET_USER_INFO(state,userinfo){//유저의 정보 세팅
      //   state.userinfo = userinfo;
      // },
      // SET_QNAS(state,payload){
      //   state.qnas = payload;
      // },
      // SET_QNA(state,payload){
      //   state.qna = payload;
      // },
      // SET_ANSWERS(state,payload){
      //   state.answers = payload;
      // }
      this.commit("SET_USER_INFO",null);
      this.commit('SET_QNAS',[]);
      this.commit('SET_QNA',{});
      this.commit('SET_ANSWERS',[]);
    },
    async checkToken(){
      console.log('checkToken : '+this._vm.$session.get('jwt'));    
      // await http.post('/tokentest?X-AUTH-TOKEN='+this._vm.$session.get('jwt')).then((response)=>{
        http.defaults.headers.common["X-AUTH-TOKEN"] = `${this._vm.$session.get('jwt')}`;
        await http.post('/tokentest',{
          'X-AUTH-TOKEN':this._vm.$session.get('jwt')
        }).then((response)=>{
        console.log("로그인 되어있는 상태");
        console.log(response);
        if(!this.state.userinfo)//없는 경우만 새로 등록해줌
          this.state.userinfo = response.data;      
      }).catch((response)=>{        
        console.log(this.state.userinfo);
        if(this.state.userinfo){
          alert("세션이 만료되어 로그아웃 됩니다.");
          this.dispatch('logout');
        }else{
          alert("로그인이 필요합니다.");
        }
        console.log('tokentest err : '+response);        
      });
    },
    async login(context,{email,password}){//로그인
      //로그인은 백엔드를 다녀와야 하니까 비동기 처리를 한다.
      console.log('login async called');
      await http.post('/login',{        
        'email':email,
        'password':password
      }).then((response)=>{        
        // console.log("로그인 시도에 대한 응답 : "+response);
        if(response.status===200 && 'data' in response){ 
          console.log(response);
          this._vm.$session.start();
          this._vm.$session.set('jwt', response.data.jwt);      
          this._vm.$session.set('userinfo', {
              name:response.data.name,
              email:response.data.email,
              code:response.data.code,
              role:response.data.role,
          });
          context.commit("SET_USER_INFO",{
              name:response.data.name,
              email:response.data.email,
              code:response.data.code,
              role:response.data.role,
          });
        }
      }).catch((response)=>{
        // console.log("로그인 에러 발생 : "+response);
        alert("email 또는 password를 확인해주세요.");
      })
    },
    async getNoticeList(context) {
      await http.get("notice/getlist").then(({ data }) => {
        context.commit("setNoticeList", data);
      })
        .catch((error) => {
          console.log(error);
        });
    },
    getNotice(context, payload) {
      console.log(payload);
      http.get("notice/select/" + payload).then(({ data }) => {
        context.commit("setNotice", data);
        console.log(data);
      }).catch((error) => {
        console.dir(error);
        console.log("실패!");
      });
    },
    getNoticeSearched(contenxt, payload) {
      console.log("찾고자하는 제목" + payload);
      http.get("notice/searchlist/" + payload).then(({ data }) => {
        contenxt.commit("setNoticeList", data);
      }).catch((error) => {
        console.dir(error);
      });
    },
    async getAptList(context) {
      await http.get("house/getlist").then(({ data }) => {
        context.commit("setAptList", data);
      })
        .catch((error) => {
          console.log(error);
        });
    },
    getAptSearched(context, payload) {
      let keyword = payload.keyword;
      let selected = payload.selected;
      console.log("검색어:" + keyword + ", 선택자:" + selected);
      http.get("house/searchlist/" + keyword + "/" + selected).then(({ data }) => {
        context.commit("setAptList", data);
        //console.log(data);
      }).catch((error) => {
        console.dir(error);
      });
    },
    async getApt(context, payload) {
      console.log("상세보기할 아파트 id : " + payload);
      await http.get("house/detail/" + payload).then(({ data }) => {
        context.commit("setApt", data);
      }).catch((error) => {
        console.dir(error);
      });
    },
    async getSpecificZone(context, payload) {
      console.log(payload);

     await http.post("house/specificzone", {
        'La1':payload.La1,
        'La2':payload.La2,
        'Ma1':payload.Ma1,
        'Ma2':payload.Ma2,
      }).then(({ data }) => {
        context.commit("setAptList", data);
      }).catch((error) => {
        console.dir(error);
      });
    },
  },
  modules: {},
});
