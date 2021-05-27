<template>
<div>
  <div class="page-header clear-filter " filter-color="black">
    <parallax
        class="page-header-image"
        style="background-image:url('/img/house_header.jpg');"
    >
    </parallax>
    <div class="container">
      <h1>아파트정보</h1>
    </div>
  </div>
  <div class="content">
    <div class="container">
      <card>
          <div class="card-body" >
              <div>
                  <input type="button" class="img-button" @click="save">
                  <h2 class="text-center font-weight-bold" >{{apt.name}}</h2>
              </div>
                <div class="text-center" style="font-size:larger">{{apt.sigungu}} {{apt.roadname}}</div>
                <img src="/img/apt.jpg">
            
              <div class="squ">
                <div class="title">
                    <h3>건물정보</h3>
                </div>
                <div class="info">
                  <div class="summary">
                    <dl><dt>주소</dt><dd>{{apt.sigungu}} {{apt.roadname}}</dd></dl>
                    <dl><dt>아파트명</dt><dd>{{apt.name}}</dd></dl>
                    <dl><dt>건축년도</dt><dd>{{apt.buildyear}}</dd></dl>
                    <dl><dt>층수</dt><dd>{{apt.floor}}</dd></dl>
                  </div>
                  <div class="summary">
                    <dl><dt>전용면적</dt><dd>{{apt.area}}</dd></dl>
                    <dl><dt>계약일</dt><dd>{{apt.contract_date}}</dd></dl>
                    <dl><dt>거래</dt><dd>{{apt.contract_type}}</dd></dl>
                    <dl><dt>매매(보증금)/월세</dt><dd>{{apt.dealamount}} / {{apt.monthlyrent}}</dd></dl>
                  </div>
                </div>
              </div>
              <div class="position">
                  <div class="squ"> </div>
                    <div class="title"><h3>위치정보</h3></div>
                    <div id="container" class="view_map">
                    <div id="mapWrapper" style="width:100%;height:300px;position:relative;">
                        <div id="map" style="width:100%;height:100%"></div> <!-- 지도를 표시할 div 입니다 -->
                        <input type="button" id="btnRoadview" @click="toggleMap(false)" title="로드뷰 보기" value="로드뷰">
                    </div>
                    <div id="rvWrapper" style="width:100%;height:300px;position:absolute;top:0;left:0;">
                        <div id="roadview" style="height:100%"></div> <!-- 로드뷰를 표시할 div 입니다 -->
                        <input type="button" id="btnMap" @click="toggleMap(true)" title="지도 보기" value="지도">
                    </div>
                </div>       
              </div>    
            </div> 
          <div class="card-footer">
           
          </div>

      </card>
    </div>
  </div>
  </div>
</template>


<script>

import { mapGetters } from "vuex";
import {Card} from '@/components';
import http from "@/util/http-common";

var map, roadview ;
export default {
  name:'buildingdetail',
  data() {
    return {
      placeOverlay :'',
      contentNode : '',
      markers : [],
      currCategory : '',
    }
  },
  components:{
      Card
  },
  computed: {
    ...mapGetters(['apt','userinfo'])
  },
  created() {
    this.$store.dispatch("getApt", `${this.$route.params.id}`).then((data)=>{
      console.log(this.apt);
    }); //this
  },//create

  mounted() {
   if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
        const script = document.createElement('script');
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initMap);
        script.src ='//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8c20713ab30fc460bab14a15ad3cdf35&libraries=services';
        document.head.appendChild(script);
    }
  }, 
  methods:{
    initMap(){
     

      var position = new kakao.maps.LatLng(this.apt.lon, this.apt.lat);
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: position,
            level: 4 // 지도의 확대 레벨
      };
      map = new kakao.maps.Map(mapContainer, mapOption);  //여기까지 지도 깔꼼

      var marker = new kakao.maps.Marker({
        position : position,
      });
      marker.setMap(map); //여기까지 마커 깔꼼

      var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
      roadview = new kakao.maps.Roadview(roadviewContainer);
      var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

      // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
      roadviewClient.getNearestPanoId(position, 50, function(panoId) {
          roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });

      // 로드뷰 초기화가 완료되면 
      kakao.maps.event.addListener(roadview, 'init', function() {

          // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다 
          var rvMarker = new kakao.maps.Marker({
              position: placePosition,
              map: roadview
          });
      });

    },//init
    toggleMap(active){
       if (active) {

        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_map"
    } else {

        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_roadview"   
    }
    },
    save(){ //매물찜!
      console.log(this.userinfo.code);
      console.log(this.$route.params.id);
      if(!this.userinfo){
        alert("로그인 먼저 해주세요");
        this.$router.push('/login');
      }else{
        http
          .post("/interestbuilding/regist",{
              member_code : this.userinfo.code,
              building_id : this.$route.params.id
          }).then(({data})=>{
              console.log("간다잇");
              this.$router.go(-1);
          });
      }
    },
  },
}
</script>

<style scoped>
h3{
  font-weight: bold;
  text-align :left;
  margin-bottom: 10px;
}
.position .title{
  border-bottom: 1px solid;
}
h1, h2{margin-bottom: 15px;}
.squ{
  position: relative;
    width: 100%;
    margin: 0 auto;
}
.title{
  margin: 0 0 30px;
}
.info{
  display: flex;
  flex-wrap: wrap;
}
.summary dl {
    display: flex;
    margin: 4px 0 0;
    line-height: 28px;
}
.summary{
  flex: 1;
  margin-right: 30px;
  padding: 21px 0 0;
  border-top: 1px solid #444;
}

.summary dt{
  position: relative;
    width: 160px;
    color: #666666;
    font-size: 18px;
}
.summary dd{
    display: flex;
    flex-wrap: wrap;
    font-size: 16px;
}
dl:first-child {
    margin: 0;
}
summary:nth-child(2n) {
    margin-right: 0;
}
input.img-button{
  background: url("/img/heart.png");
  border : none;
  width: 32px;
  height: 32px;
  cursor: pointer;
  float: right;
}

.page-header{min-height: 15vw;}
.map_wrap, .map_wrap * {margin:0; padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap {position:relative;width:100%;height:350px;}
#category {position:absolute;border-radius: 5px; margin: 5px; padding-left: 0; border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);background: #fff;overflow: hidden;z-index: 2;}
/* #category {position:absolute;top:10px;left:10px;border-radius: 5px; border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);background: #fff;overflow: hidden;z-index: 2;} */
#category li {float:left;list-style: none;width:50px;border-right:1px solid #acacac;padding:6px 0;text-align: center; cursor: pointer;}
#category li.on {background: #eee;}
#category li:hover {background: #ffe6e6;border-left:1px solid #acacac;margin-left: -1px;}
#category li:last-child{margin-right:0;border-right:0;}
#category li span {display: block;margin:0 auto 3px;width:27px;height: 28px;}
#category li .category_bg {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;}
#category li .bank {background-position: -10px 0;}
#category li .mart {background-position: -10px -36px;}
#category li .pharmacy {background-position: -10px -72px;}
#category li .oil {background-position: -10px -108px;}
#category li .cafe {background-position: -10px -144px;}
#category li .store {background-position: -10px -180px;}
#category li.on .category_bg {background-position-x:-46px;}
.placeinfo_wrap {position:absolute;bottom:28px;left:-150px;width:300px;}
.placeinfo {position:relative;width:100%;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;padding-bottom: 10px;background: #fff;}
.placeinfo:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
.placeinfo_wrap .after {content:'';position:relative;margin-left:-12px;left:50%;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
.placeinfo a, .placeinfo a:hover, .placeinfo a:active{color:#fff;text-decoration: none;}
.placeinfo a, .placeinfo span {display: block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
.placeinfo span {margin:5px 5px 0 5px;cursor: default;font-size:13px;}
.placeinfo .title {font-weight: bold; font-size:14px;border-radius: 6px 6px 0 0;margin: -1px -1px 0 -1px;padding:10px; color: #fff;background: #d95050;background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
.placeinfo .tel {color:#0f7833;}
.placeinfo .jibun {color:#999;font-size:11px;margin-top:0;}

#container {overflow:hidden;height:300px;position:relative;}
#btnRoadview,  #btnMap {position:absolute;top:5px;left:5px;padding:7px 12px;font-size:14px;border: 1px solid #dbdbdb;background-color: #fff;border-radius: 2px;box-shadow: 0 1px 1px rgba(0,0,0,.04);z-index:1;cursor:pointer;}
#btnRoadview:hover,  #btnMap:hover{background-color: #fcfcfc;border: 1px solid #c1c1c1;}
#container.view_map #mapWrapper {z-index: 10;}
#container.view_map #btnMap {display: none;}
#container.view_roadview #mapWrapper {z-index: 0;}
#container.view_roadview #btnRoadview {display: none;}
</style>

