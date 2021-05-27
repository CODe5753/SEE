import axios from "axios";
import router from '../router';
import store from '../store';
// axios 객체 생성

const instance = axios.create({
  baseURL: 'http://localhost:9999',
  headers: {
    "Content-type": "application/json",
    'X-Naver-ClientId':'vuVCvv_eMPr09W2JvQCT',
    'X-Naver-Client-Secret':'vxZJ2xnpie'
  },
  timeout:1000
});

instance.interceptors.request.use(
  function (config) {
    if(JSON.parse(localStorage.getItem('vue-session-key')).jwt){
      config.headers.common["X-AUTH-TOKEN"] = JSON.parse(localStorage.getItem('vue-session-key')).jwt;
    }
      // 요청 바로 직전
      // axios 설정값에 대해 작성합니다.
      console.log('req done');
      return config;
  }, 
  function (error) {
      // 요청 에러 처리를 작성합니다.
      console.log('req err');
      return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  function (response) {
  /*
      http status가 200인 경우
      응답 바로 직전에 대해 작성합니다. 
      .then() 으로 이어집니다.
  */
      console.log('resp done'); 
      return response;
  },

  function (error) {
  /*
      http status가 200이 아닌 경우
      응답 에러 처리를 작성합니다.
      .catch() 으로 이어집니다.    
      */
    store.dispatch('logout');
    if(error.code){//timeout 뜨는 경우
      alert('연결이 불안정합니다.');
    }else{
      router.push('/login');
      // alert('로그인이 필요합니다.');
    }
    // if(error.response.status==403){//로그인 필요한 상황
    //   $store.dispatch('logout'); 
    // }
    return Promise.reject(error);
  }
);

// 생성한 인스턴스를 익스포트 합니다.
export default instance;


//--------------------------이전버전
// export default axios.create({
//   // baseURL: "http://125.242.36.128:9999",
//   baseURL: "http://localhost:9999",
//   headers: {
//     "Content-type": "application/json"
//   }
// });


// http.interceptors.request.use(function(config){
//   console.log('axios intercepter request'+config);
//     return config;
// },function(error){
//   alert('axios global request err : '+error);
//   return Promise.reject(error);
// });
