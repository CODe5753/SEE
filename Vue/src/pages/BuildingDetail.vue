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
                    <div id="map" style="width:100%;height:400px;">
                    <ul id="category">
                      <li id="BK9" data-order="0"> 
                          <span class="category_bg bank"></span>
                          은행
                      </li>       
                      <li id="MT1" data-order="1"> 
                          <span class="category_bg mart"></span>
                          마트
                      </li>  
                      <li id="PM9" data-order="2"> 
                          <span class="category_bg pharmacy"></span>
                          약국
                      </li>  
                      <li id="OL7" data-order="3"> 
                          <span class="category_bg oil"></span>
                          주유소
                      </li>  
                      <li id="CE7" data-order="4"> 
                          <span class="category_bg cafe"></span>
                          카페
                      </li>  
                      <li id="CS2" data-order="5"> 
                          <span class="category_bg store"></span>
                          편의점
                      </li>      
                  </ul></div>
              </div>
            </div>
            <h4 class="card-title "></h4>
          <div class="card-footer">
            <br><br><br><br>    
              <h4>test좀해보자..~</h4>             
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

var map;
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

      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption); 

      console.log(this.apt);
      console.log(this.apt.lon +" "+ this.apt.lat)
      this.map.setCenter(new kakao.maps.LatLng(this.apt.lon, this.apt.lat));
      var mposition = new kakao.maps.LatLng(this.apt.lon, this.apt.lat);
      var marker = new kakao.maps.Marker({
        position : mposition,
      });
      marker.setMap(this.map);

      this.areaInfo();

    },//init
    save(){
      alert("오호라");
      console.log(this.userinfo.code);
      console.log(this.$route.params.id);
      if(this.userinfo==null){
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
  areaInfo(){
      this.placeOverlay = new kakao.maps.CustomOverlay({zIndex:1});
      this.contentNode = document.createElement('div') // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다 
      this.markers = []; // 마커를 담을 배열입니다
      this.currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places(this.map); 

      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, 'idle', this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다 
      this.contentNode.className = 'placeinfo_wrap';

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다 
      this.addEventHandle(this.contentNode, 'mousedown', kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, 'touchstart', kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);  

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();
  },//areainfo
  addEventHandle(target, type, callback){
    if (target.addEventListener) {
        target.addEventListener(type, callback);
    } else {
        target.attachEvent('on' + type, callback);
    }
  },//addEventHandle
  searchPlaces(){
    if (!this.currCategory) {
        return;
    }
    
    // 커스텀 오버레이를 숨깁니다 
    placeOverlay.setMap(null);

    // 지도에 표시되고 있는 마커를 제거합니다
    this.removeMarker();
    
    ps.categorySearch(this.currCategory, this.placesSearchCB, {useMapBounds:true}); 
  },//searchPlaces
  placesSearchCB(data, status, pagination){
     if (status === kakao.maps.services.Status.OK) {

        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요

    } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
    }
  },//placesSearchCB
  displayPlaces(places){
    // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
    // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
    var order = document.getElementById(this.currCategory).getAttribute('data-order');


    for ( var i=0; i<places.length; i++ ) {

            // 마커를 생성하고 지도에 표시합니다
            var marker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

            // 마커와 검색결과 항목을 클릭 했을 때
            // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
            (function(marker, place) {
                kakao.maps.event.addListener(marker, 'click', function() {
                    this.displayPlaceInfo(place);
                });
            })(marker, places[i]);
    }
  },//displayPlaces

  addMarker(position, order) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage 
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
  },//addMarker

  removeMarker(){
    for ( var i = 0; i < this.markers.length; i++ ) {
        this.markers[i].setMap(null);
    }   
    this.markers = [];
  }, //removeMarker
  displayPlaceInfo(place){
     var content = '<div class="placeinfo">' +
                    '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';   

    if (place.road_address_name) {
        content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
                    '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
    }  else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
    }                
   
    content += '    <span class="tel">' + place.phone + '</span>' + 
                '</div>' + 
                '<div class="after"></div>';

    this.contentNode.innerHTML = content;
    this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
    this.placeOverlay.setMap(map);  
  }, //displayPlace

  addCategoryClickEvent() {
    var category = document.getElementById('category')
    var children = category.children;

    for (var i=0; i<children.length; i++) {
        // children[i].onclick = this.onClickCategory();
        children[i].onclick = function(event){
          console.log('onClickCategory in '+event);
          var id = this.id,
          className = this.className;

          this.placeOverlay.setMap(null);

          if (className === 'on') {
              this.currCategory = '';
              this.changeCategoryClass();
              this.removeMarker();
          } else {
              this.currCategory = id;
              this.changeCategoryClass(this);
              this.searchPlaces();
          }
        };
    }
  }, //addCategory
  changeCategoryClass(el) {
    var category = document.getElementById('category'),
        children = category.children,
        i;

    for ( i=0; i<children.length; i++ ) {
        children[i].className = '';
    }

    if (el) {
        el.className = 'on';
    }
  }, //change 
  },
}
</script>

<style scoped>
h3{
  font-weight: bold;
}
.position .title{
  border-bottom: 1px solid;
}
h1, h2{margin-bottom: 15px;}
.squ{
  position: relative;
    width: 1080px;
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


</style>

