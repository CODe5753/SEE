package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.InterestBuildingDto;
import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.service.HouseService;
import com.ssafy.happyhouse.model.service.InterestBuildingService;
import com.ssafy.happyhouse.model.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/interestbuilding")
@Api("관심건물 컨트롤러 API V1")
@CrossOrigin("*")
public class InterestBuildingController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private InterestBuildingService interestbuildingService;

	@Autowired
	HouseService houseService;
	
	@ApiOperation(value = "관심건물 등록", notes = "관심건물 db에 저장.")
	@PostMapping("/regist")
	public ResponseEntity<String> regist(@RequestBody @ApiParam(value = "관심건물 등록", required = true) InterestBuildingDto interestbuildingDto) {
		logger.info("[Called] 관심건물 등록");
		try {
			int member_code = interestbuildingDto.getMember_code();
			List<HouseInfoDto> list = houseService.getInteresetBuilding(member_code);//중복체크
			for(HouseInfoDto hid:list) {
				if(hid.getId()==interestbuildingDto.getBuilding_id()) {//이미 존재하는 관심목록
					return new ResponseEntity<String>("overlap",HttpStatus.OK);//수행은 되었지만 이미 있는 상품이므로 202번 코드 반환
				}
			}
			if(interestbuildingService.addInterestBuilding(interestbuildingDto)) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@ApiOperation(value = "관심 건물 목록 조회", notes="특정 유저의 관심 건물에 대한 정보를 반환합니다.")
	@GetMapping("/{member_code}")
	public ResponseEntity<List<HouseInfoDto>> getinterestbuilding(@PathVariable("member_code") @ApiParam(value="HouseInfo info") int member_code) {
		logger.info("[Called] 유저("+member_code+") 목록조회");
		List<HouseInfoDto> list = houseService.getInteresetBuilding(member_code);
		logger.info("[Result] 관심 건물 목록 갯수 : "+list.size());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<HouseInfoDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "관심건물 삭제", notes ="관심건물을 삭제합니다.")
	@DeleteMapping(value ="/remove")
	public ResponseEntity<List<NoticeDto>> remove(@RequestBody @ApiParam(value = "관심건물 삭제", required = true) InterestBuildingDto interestbuildingDto) {
		logger.info("[Called] 관심건물 삭제");
		interestbuildingService.deleteInterestBuilding(interestbuildingDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	

}