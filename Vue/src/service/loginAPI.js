import http from "@/util/http-common";

const getUserInfo = (email,password) =>{
    return http.post('/login',{        
        'email':email,
        'password':password
    }).then((response)=>{
        console.log("로그인 시도에 대한 응답 : "+response);
        if(response.status===200 && 'data' in response){
            //store를 통해 정보 전달
        }
    })
}