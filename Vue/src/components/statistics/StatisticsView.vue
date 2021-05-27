<template>
  <div class="border">
    <h3 class="title">통계자료 분석</h3>
    <button class="btn btn-success" @click="getPopulation">거주 인구 분석</button>
    <!-- <button class="btn btn-warning" @click="makeChart">차트 생성</button> -->
    <button class="btn btn-info">거주 인구 분석</button>
    <button class="btn btn-default">거주 인구 분석</button>
    <p>{{statisfics}}</p>
    <div class="container">
        <div class="col-md-6">
            <canvas id="myChart" style="width:200px; height:200px;"></canvas>
        </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            statisfics:'',
        }
    },
    created() {
    },
    methods: {   
        getPopulation(){            
            // axios.get('https://sgisapi.kostat.go.kr/OpenAPI3/startupbiz/pplsummary.json?acm_cd=11040&accessToken=5934462d-d332-43ab-8948-0405d958dbcf')
            axios.get('https://sgisapi.kostat.go.kr/OpenAPI3/startupbiz/pplsummary.json?adm_cd=11040&accessToken=1c1c8646-77eb-4587-8a32-78f1b2b604fb')
            .then((resp)=>{
                // let origin = resp.data;
                const res = resp.data.result; 
                console.log(resp);
                const labels = ["10대 미만","10대","20대","30대","40대","50대","60대","70대 이상"];
                const data = [res.teenage_less_than_cnt,res.teenage_cnt,res.twenty_cnt,res.thirty_cnt,res.forty_cnt,res.fifty_cnt,res.sixty_cnt,res.seventy_more_than_cnt];
                var backgroundcolors = [];
                var bordercolors = [];
                for(var i =0;i<9;i++){
                    backgroundcolors.push(this.randomColors());
                    bordercolors.push(this.randomColors());
                }//색상 랜덤 추출
                
                const datasets = [{
                    label:'인구수',
                    data : data,
                    backgroundColor:backgroundcolors,
                    bordercolors:bordercolors,
                    borderWidth: 2,
                    barThickness: 36,
                }
                ];
                this.makeChart(labels,datasets);
            })            
        },
        makeChart(labels,datasets){
            const myChartOne = document.getElementById("myChart").getContext("2d");            
            const data = new Chart(myChartOne, {
                type: "doughnut", // pie, line, donut, polarArea ...
                data: {
                    labels: labels,
                    datasets:datasets
                    // datasets: [
                    // {
                    //     label: datasets.label,
                    //     data: datasets.data,
                    //     backgroundColor: [
                    //     "red",
                    //     "rgba(255, 255, 0, 0.5)",
                    //     "purple",
                    //     "orange",
                    //     "lightblue",
                    //     ],
                    //     borderColor: [
                    //     "rgba(255, 99, 132, 1)",
                    //     "rgba(54, 162, 235, 1)",
                    //     "rgba(255, 206, 86, 1)",
                    //     "rgba(75, 192, 192, 1)",
                    //     "rgba(153, 102, 255, 1)",
                    //     "rgba(255, 159, 64, 1)",
                    //     ],
                    //     borderWidth: 2,
                    //     barThickness: 36,
                    // },
                    // ],
                },
                });
            // this.createChart('myChart',data);
        },
        randomColors(){//랜덤한 색상 반환
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgba(" + r + "," + g + "," + b + ",0.8)";
        }
    },    
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
