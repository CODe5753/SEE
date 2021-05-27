<template>
  <div class="page-header clear-filter" style="position:fixed;overflow:hidden;" filter-color="black">
    <div
      class="page-header-image"
      style="background-image: url('img/login.jpg')"
    ></div>
    <div class="content">
      <div class="container">
        <div id="mapWrapper">
          <div id='map'></div>
          <div id='currentLocation' @click="currentLocation"><img src="img/current_location.png"/></div>
          <div id='searchControl'>
            <form-group-input class="form-control input has-large"
                style="background-color:white !important; left:10px; width:400px"
                placeholder="건물명, 주소로 검색해 보세요" 
                v-model="keyword"
                @keyup.enter="search(keyword)"
                />
          </div>
          <div v-if="apts" id="menu_wrap" class="bg_white">
          <ul id="placesList"></ul>
          <div id="pagination">
          </div>
        </div>
          
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import {FormGroupInput} from '@/components';
import {mapGetters} from "vuex";
import gu from '../resource/gu.json'
import dong from '../resource/file.json'
import sido from '../resource/sido.json'

import korea from '../resource/korea.json';

var map;
export default{
  name:'Map',
  // props:{
  //   keyword:{
  //     type:String,
  //     default:''
  //   }
  // },
  components:{
    FormGroupInput
  },
   computed:{
     ...mapGetters(["apts"]),
  },
  data() {
    return {
      keyword : this.keyword,
      sigunguname : [],
      sigungu : [],
      sigunguoverlay : [],

      dongoverlay : [],

      sidooverlay : [],

      markers :[],
      markeroverlay : [],

      koreaPolygon : [],

    }
  },
  mounted() {
    console.log(this.apts);
   if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
        const script = document.createElement('script');
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initMap);
        script.src =
          '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8c20713ab30fc460bab14a15ad3cdf35&libraries=services';
        document.head.appendChild(script);
    }
  }, 
  methods:{
    initMap(){
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div
          mapOption = {
            center: new kakao.maps.LatLng(36.54351, 127.89661), // 지도의 중심좌표
            level: 12 // 지도의 확대 레벨
          };
      map = new kakao.maps.Map(mapContainer, mapOption);

  
          this.mouse();
          this.makeKoreaPolygon();
          this.makeArea(sido,this.sidooverlay)
          this.makeGu();
          this.makeArea(dong,this.dongoverlay);
          this.findoverlay(this.sidooverlay);

      //this.currentLocation();
      if(this.$route.params.keyword){ //메인페이지에서 호출
          console.log(this.$route.params.keyword);
          this.search(this.$route.params.keyword);
      }
    }, // init
      mouse(){
        var ref = this;
          var dragend = function(){
              var level = map.getLevel();
              console.log(level);
              if(level>=9){ //시정보
                 ref.findoverlay(ref.sidooverlay);
                 ref.seehide(map);
              }else if(level>=7){ //구정보
                 ref.findoverlay(ref.sigunguoverlay);
                 ref.seehide(null);
              }else if(level>=5){ //동정보
                 ref.findoverlay(ref.dongoverlay);
                 ref.markerreset();
              }else if(level>=3){ // 현재 지도 좌표안에있는 건물정보 출력
                  ref.overlayreset(ref.sidooverlay);
                  ref.overlayreset(ref.sigunguoverlay);
                  ref.overlayreset(ref.dongoverlay);
                  ref.latlng();
              }
          }
          kakao.maps.event.addListener(map, 'dragend', dragend);
          kakao.maps.event.addListener(map, 'zoom_changed', dragend);        
        },//mouse
        
    makeKoreaPolygon(){
      const res = JSON.parse(JSON.stringify(korea)).features;
      res.forEach(element => {

        var polygonPath = [];
          (element.geometry.coordinates).forEach(ele=>{
            ele.forEach(el =>{
              polygonPath.push(new kakao.maps.LatLng(el[1],el[0]));
              });//el
              
              var polygon = new kakao.maps.Polygon({
                map : map,
                path : polygonPath,
                strokeWeight :1,
                strokeColor : '#004c80',
                strokeOpacity : 0.8,
                fillColor : '#fff',
                fillOpacity : 0.7
            });
            // polygon.setMap(map);
            kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
                polygon.setOptions({fillColor: '#09f'});
                    
            });
            //   다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
            // 커스텀 오버레이를 지도에서 제거합니다 
            kakao.maps.event.addListener(polygon, 'mouseout', function() {
                polygon.setOptions({fillColor: '#fff'});
                
            });
            // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
            kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
                var level = map.getLevel()-1;

                var lating = mouseEvent.latLng;
                map.setLevel(level,{anchor : new kakao.maps.LatLng(lating.Ma,lating.La),animate:{
                    duration : 350
                }});
            });  

            this.koreaPolygon.push(polygon);       
            polygonPath =[];
          });//ele
      });//res
    },

    seehide(value){
      for (let index = 0; index < this.koreaPolygon.length; index++) {
        this.koreaPolygon[index].setMap(value);
      }
    },


    latlng(){
       var bounds = map.getBounds();
        let ref = this;
                // 영역정보의 남서쪽 정보를 얻어옵니다 
            var swLatlng = bounds.getSouthWest();
                    
                // 영역정보의 북동쪽 정보를 얻어옵니다 
            var neLatlng = bounds.getNorthEast();
                
            let payload ={
                La1 : swLatlng.La,
                La2 : neLatlng.La,
                Ma1 : swLatlng.Ma,
                Ma2 : neLatlng.Ma,
            }
            ref.$store.dispatch("getSpecificZone",payload).then((data)=>{
              console.log(data);
              ref.makeMarker();
            });
    },

    findoverlay(overlay){
        var ref = this;
        var bounds = map.getBounds();
        // 영역정보의 남서쪽 정보를 얻어옵니다 
        var swLatlng = bounds.getSouthWest();
            
        // 영역정보의 북동쪽 정보를 얻어옵니다 
        var neLatlng = bounds.getNorthEast();

        ref.overlayreset(ref.sidooverlay);
        ref.overlayreset(ref.sigunguoverlay);
        ref.overlayreset(ref.dongoverlay);
        

        for (let index = 0; index < (overlay.length); index++) {
            const element = (overlay)[index];

            var la = element.getPosition().La;
            var ma = element.getPosition().Ma;

            if(la>=swLatlng.La && la<=neLatlng.La && ma>=swLatlng.Ma && ma<=neLatlng.Ma){
                element.setMap(map);
            }
        }
    }, // findoverlay

     overlayreset(saveoverlay){
        for (let index = 0; index < saveoverlay.length; index++) {
            const element = (saveoverlay)[index];
            element.setMap(null);
        }
    },//overlayreset

    markerreset(){
      var ref = this;
        for (let index = 0; index < ref.markers.length; index++) {
            const element = ref.markers[index];
            const overlay = ref.markeroverlay[index];
            element.setMap(null);
            overlay.setMap(null); 
        }
        ref.markers=[];
        ref.markeroverlay=[];
    },

     makeArea(area,overlay){
        console.log("make");

         const res = JSON.parse(JSON.stringify(area))
            res.forEach(element => {
                this.custommarker(element.위도,element.경도,element.이름, overlay);
            }); //res
      },//makeGu

        makeGu(){

        console.log("makeGu");
        const res = JSON.parse(JSON.stringify(gu));
        
        (Object.keys(res)).forEach(element=>{
            //console.log(element);
            this.sigunguname.push(element);
        });
            
        (Object.values(res)).forEach(element=>{
            this.sigungu.push(element);
            
        });
        this.guCenter(this.sigunguname,this.sigungu);
      },//makeGu

    guCenter(areaname, areaposition){
            
        for (let index = 0; index < areaname.length; index++) {
            const name = areaname[index].split('/')[1];
            const position = areaposition[index];
            this.custommarker(position.lat,position.long,name, this.sigunguoverlay);
            } //for
    },//guCenter

    custommarker(x,y,name, saveoverlay){

        var position = new kakao.maps.LatLng(x, y);
        var customeoverlay = new kakao.maps.CustomOverlay({
                map : '',
                position : position,
                content : '',
        });

            var content = document.createElement('div');
            content.style.cssText = 'border-radius:10px; background-color: cadetblue; color: cornsilk; font-size: x-small';
            
            var clickBtn = document.createElement('div');
            clickBtn.innerHTML = name;
            clickBtn.onclick= function(){
                var level = map.getLevel();
                if(level>3){
                    map.setLevel(level-1,{anchor : position,animate:{
                    duration : 350}});
                }
                console.log(name);
            };

            content.appendChild(clickBtn);
            customeoverlay.setContent(content);
            saveoverlay.push(customeoverlay);


    },//custommarker

    search(keyword){
      console.log(keyword);
      let vm = this;
      var geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(keyword, function(result, status) {
      // 정상적으로 검색이 완료됐으면 
      if (status === kakao.maps.services.Status.OK) {
       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
       
        map.setCenter(new kakao.maps.LatLng(coords.Ma,coords.La));
        map.setLevel(4);
        vm.seehide(null);
        vm.latlng();
        vm.makeMarker();
        
      }else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
      } 
    });  //geocoder   
    },//search

    makeMarker()
    {
      var ref = this;

          var listEl = document.getElementById('placesList');
          var menuEl = document.getElementById('menu_wrap');
          var fragment = document.createDocumentFragment();

          this.removeAllChildNods(listEl);

          console.log("makeMarker!!!"+this.apts.length);
          
          var icon = new kakao.maps.MarkerImage(
                'https://i.ibb.co/6v5CWVn/marker.png',
                new kakao.maps.Size(5, 5),
                {
                    alt: "마커 이미지",
                    shape: "poly",
                }
          );
          for (let index = 0; index < this.apts.length; index++) {
              const element = this.apts[index];
              //console.log(element);

              var marker = new kakao.maps.Marker({
              map: this.map,
              position: new kakao.maps.LatLng(element.lon,element.lat),
              image : icon,
              });

              marker.setMap(map);
              this.markers.push(marker);

              var overlay = new kakao.maps.CustomOverlay({
                        map : map,
                        position: marker.getPosition()       
              });
              var price;
              if(element.contract_type=="월세") price = element.monthlyrent+"만원";
              else if(element.dealamount/10000>1) price = element.dealamount/10000+"억";
              else price = element.dealamount+"천만원"

              var content = document.createElement('div');
              content.className = "wrap";

               var infos = document.createElement('div');
              infos.className = "infos";

              content.appendChild(infos);

              var title = document.createElement('div');
              title.className = "title";
              title.innerHTML = element.contract_type;

              infos.appendChild(title);

              var body = document.createElement('div');
              body.className = "body";

              infos.appendChild(body);

              var desc = document.createElement('div');
              desc.className = "desc";

              body.appendChild(desc);

              var ellipsis = document.createElement('div');
              ellipsis.className = "ellipsis";
              ellipsis.innerHTML = price;

              var jibun = document.createElement('a');
              jibun.className = "jibun";
              jibun.innerHTML = "상세보기"
              jibun.onclick = function(){
                ref.$router.push({name:'buildingdetail',params:{'id': element.id}});
              };
              desc.appendChild(ellipsis);
              desc.appendChild(jibun);
              
              overlay.setContent(content);

              overlay.setMap(map);
              this.markeroverlay.push(overlay);

              var itemEl = this.getListItem(index,element);

              var ref = this;
              itemEl.onclick = function(){
                console.log(element);
                ref.$router.push({name:'buildingdetail',params:{'id': element.id}});
              }
              
              fragment.appendChild(itemEl);
              
          }   //for

          // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
          listEl.appendChild(fragment);
          console.log(listEl);
          menuEl.scrollTop = 0;
    }, //makeMarker
    move(){
      alert("잘와따");
    },
    
    // 검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, element) {

        var el = document.createElement('li');
        var itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                    '<div class="info">' +
                    '   <h5>' + element.name + '</h5>'
                    + '<span>'+element.sigungu+" "+'</span>'+
                    '<span>'+element.roadname+'</span>' +
                    '<span>'+element.contract_type+" ";
        if(element.contract_type=='월세'){
          itemStr += element.monthlyrent+"만원";
        }else {
          itemStr +=element.dealamount/1000+"억원";
        }
        el.innerHTML = itemStr;
        el.className = 'item';

        return el;
    }, //getListItem


   

    // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
    displayPagination(pagination) {
        console.log("displayPagination !!!"+pagination);
        var paginationEl = document.getElementById('pagination');
        var fragment = document.createDocumentFragment();
        var i; 

        // 기존에 추가된 페이지번호를 삭제합니다
        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild (paginationEl.lastChild);
        }

        for (i=1; i<=pagination.last; i++) {
            var el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i===pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (function(i) {
                    return function() {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    },


    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {   
        while (el.hasChildNodes()) {
            el.removeChild (el.lastChild);
        }
    },
    
    currentLocation(){ 

      //geoLocation 객체 사용 (현재 위치 가져오기)
      if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도
            
            var locPosition = new kakao.maps.LatLng(lat, lon) // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                       
            var marker = new kakao.maps.Marker({  
                map: map, 
                position: new kakao.maps.LatLng(locPosition.Ma, locPosition.La),
            }); 
            
            marker.setMap(map);
            map.setLevel(4);
            map.setCenter(new kakao.maps.LatLng(locPosition.Ma, locPosition.La));
        });
      }
    },
  },
}
</script>

<style scoped>
#mapWrapper{
  top:85px;
  left:0px;
  width:100%;
  height:100%;
  position:fixed;
}
#map{
  width:100%;
  height:100%;
  position:fixed;
}
#currentLocation{
  position:absolute;
  top:900px;left:1000px;width:42px;height:42px;z-index: 1;
  cursor: pointer; 
  background-color: white;
  /* background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/common/img_search.png) 0 -450px no-repeat; */
}
input.form-control{
  width: 350px;
    margin-top: 5px;
    margin-left: 60px;
}
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000 !important;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:500px;}
#menu_wrap {position:absolute;top:20px;left:0;bottom:0;height:800px;width:20vw;margin:50px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.9);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#ffffff; opacity: 1 !important;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList {padding-left: 0;}
#placesList li {list-style: none;}
.item:hover { background: #7531c277;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
.info>*{color:black !important;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}
</style>

<style>
    .title{text-align: center !important;}
    .wrap {position: absolute;left: 0;bottom: 3px;width: 75px;height: 82px;margin-left: -35px;text-align: center;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .infos {width: 70px;height: 70px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .infos:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .infos .title {margin-bottom:0px;padding: 3px 0 0 0px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;color: darkslateblue;}
    .infos .body {position: relative;overflow: hidden;}
    .infos .desc {position: relative;margin: 0px 0 0 0px;height: 35px;color: black;}
    .desc .ellipsis {font-size:15px; font-weight: bold;  overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
    .infos:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
</style>