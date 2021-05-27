package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.service.HouseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/house")
@Api("HouseInfo 컨트롤러 API V1")
@CrossOrigin("*")
public class HouseController {
	
	private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
	
	@Autowired
	HouseService houseService;
	
	@ApiOperation(value="House 이미지를 가져옴")
	@PostMapping("/getimage")
	public ResponseEntity<String> getimage(@RequestBody @ApiParam(value = "이미지 검색어") Map<String,String> map){
		logger.info("이미지 검색어 : "+map.get("keyword"));
			String keyword = map.get("keyword");
	        String clientId = "cC7WjpwXayQ0glYzWA_z";//애플리케이션 클라이언트 아이디값";
	        String clientSecret = "RYIrKUmtFK";//애플리케이션 클라이언트 시크릿값";
	        try {
	            String query = URLEncoder.encode(keyword, "UTF-8");
	            String apiURL = "https://openapi.naver.com/v1/search/image?query=" + query + "?display=1&sort=sim";
//	            String apiURL = "https://openapi.naver.com/v1/search/image.xml?query=%EC%A3%BC%EC%8B%9D&display=10&start=1&sort=sim";
	            logger.info(apiURL);
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            logger.info(response.toString());
	            return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
//	            System.out.println(response.toString());
	        } catch (Exception e) {
	        	return new ResponseEntity(HttpStatus.NO_CONTENT);
//	            System.out.println(e);
	        }
	}
	@ApiOperation(value="House 연관 뉴스를 가져옴")
	@PostMapping("/getnews")
	public ResponseEntity<String> getnews(@RequestBody @ApiParam(value = "뉴스 검색어") Map<String,String> map){
		logger.info("뉴스 검색어 : "+map.get("keyword"));
			String keyword = map.get("keyword");
	        String clientId = "cC7WjpwXayQ0glYzWA_z";//애플리케이션 클라이언트 아이디값";
	        String clientSecret = "RYIrKUmtFK";//애플리케이션 클라이언트 시크릿값";
	        try {
	            String query = URLEncoder.encode(keyword, "UTF-8");
	            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + query + "?display=1&sort=sim";
	            logger.info(apiURL);
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            logger.info(response.toString());
	            return new ResponseEntity<String>(response.toString(),HttpStatus.OK);
//	            System.out.println(response.toString());
	        } catch (Exception e) {
	        	return new ResponseEntity(HttpStatus.NO_CONTENT);
//	            System.out.println(e);
	        }
	}
	@ApiOperation(value = "HouseInfo 목록 조회", notes="HouseInfo에 대한 모든 정보를 반환합니다.")
	@GetMapping("/getlist")
	public ResponseEntity<List<HouseInfoDto>> getlist() {
		logger.info("[Called] HouseInfo 목록조회");
		List<HouseInfoDto> list = houseService.getlist();
		logger.info("[Result] HouseInfo 목록 갯수 : "+list.size());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<HouseInfoDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "HouseInfo 목록 조회", notes="HouseInfo에 대한 모든 정보를 반환합니다.")
	@GetMapping("/getinerestingbuilding/{member_code}")
	public ResponseEntity<List<HouseInfoDto>> getinterestbuilding(@PathVariable("member_code") @ApiParam(value="HouseInfo info") int member_code) {
		logger.info("[Called] HouseInfo 목록조회");
		List<HouseInfoDto> list = houseService.getInteresetBuilding(member_code);
		logger.info("[Result] HouseInfo 목록 갯수 : "+list.size());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<HouseInfoDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "HouseInfo 특정 구역 목록 조회", notes="특정구역에 대한 대한 모든 정보를 반환합니다.")
	@PostMapping("/specificzone")
	public ResponseEntity<List<HouseInfoDto>> specificzone(@RequestBody @ApiParam(value = "위도 경도 값") Map<String, String> map) {
		logger.info("[Called] HouseInfo 특정 구역 목록조회");
		logger.info("대체 무슨값이들어와?"+map);
		logger.info("La1 :"+map.get("La1"));
		List<HouseInfoDto> list = houseService.specificzone(map);
		logger.info("listsize"+list.size());
		logger.info("[Result] HouseInfo 목록 갯수 : "+list.size());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<HouseInfoDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	
	@ApiOperation(value = "HouseInfo 상세정보 보기", notes ="해당하는 HouseInfo 상세정보를 반환합니다.")
	@GetMapping("/detail/{id}")
	public ResponseEntity<HouseInfoDto> detail(@PathVariable("id") @ApiParam(value="HouseInfo name") int id) {
		logger.info("[Called] HouseInfo 상세정보 조회");
		HouseInfoDto houseinfoDto = houseService.detail(id);
		logger.info(houseinfoDto.getName());
		if(houseinfoDto!=null)
			return new ResponseEntity<HouseInfoDto>(houseinfoDto,HttpStatus.OK);
		else 
			return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "HouseInfo 상세 검색", notes="HouseInfo 상세 검색한 결과를 반환합니다.")
	@GetMapping("/searchlist/{keyword}/{selected}")
	public ResponseEntity<List<HouseInfoDto>> search(@PathVariable("keyword") @ApiParam(value="검색할 검색어") String keyword,@PathVariable("selected") @ApiParam(value="선택자") String selected){
		logger.info("search called... => search:"+keyword+" selected:"+selected);
		Map<String,String> map = new HashMap<String, String>();
		map.put("select", selected);
		map.put("search", keyword);
		
		List<HouseInfoDto> list = houseService.searchlist(map);		
		return new ResponseEntity(list,HttpStatus.OK);		
	}
}
