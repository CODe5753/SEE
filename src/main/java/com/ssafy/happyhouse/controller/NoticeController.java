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


import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/notice")
@Api("HouseInfo 컨트롤러 API V1")
@CrossOrigin("*")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@ApiOperation(value = "Notice 목록 조회", notes="공지사항에 대한 모든 정보를 반환합니다.")
	@GetMapping("/getlist")
	public ResponseEntity<List<NoticeDto>> getlist() {
		logger.info("[Called] Notice 목록조회");
		List<NoticeDto> list = noticeService.getlist();
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<NoticeDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "공지사항 등록", notes = "공지사항 DB에 저장.")
	@PostMapping("/regist")
	public ResponseEntity<List<NoticeDto>> regist(@RequestBody @ApiParam(value = "게시글 등록", required = true) NoticeDto noticeDto) {
		logger.info("[Called] 공지사항 등록");
		try {
			if(noticeService.regist(noticeDto)) {
				List<NoticeDto> list = noticeService.getlist();
				return new ResponseEntity<List<NoticeDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "특정 공지사항 게시글검색", notes ="해당하는 게시글 정보 가져옴")
	@GetMapping(value = "/select/{no}")
	public ResponseEntity<NoticeDto> select(@PathVariable("no") @ApiParam(value="검색 할 게시글번호") int no) {
		logger.info("[Called] 공지사항 게시글 검색");
		NoticeDto noticeDto = noticeService.select(no);
		if(noticeDto != null)
			return new ResponseEntity<NoticeDto>(noticeDto, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	
	@ApiOperation(value = "Notice 게시글 수정", notes ="게시글을 수정합니다.")
	@PutMapping(value = "/modify")
	public ResponseEntity<List<NoticeDto>> update(@RequestBody @ApiParam(value = "수정 할 게시글 정보") NoticeDto noticeDto) {
		logger.info("어떻게오는건데?"+noticeDto);
		logger.info("[Called] 공지사항 게시글 수정");
		noticeService.modify(noticeDto);
		List<NoticeDto> list = noticeService.getlist();
		return new ResponseEntity<List<NoticeDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 게시글 삭제", notes ="게시글을 삭제합니다.")
	@DeleteMapping(value = "/remove/{no}")
	public ResponseEntity<List<NoticeDto>> remove(@PathVariable("no") @ApiParam(value = "삭제 할 게시글 no") int no) {
		logger.info("[Called] 공지사항 게시글 삭제");
		noticeService.remove(no);
		List<NoticeDto> list = noticeService.getlist();
		return new ResponseEntity<List<NoticeDto>>(list, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "공지사항  검색", notes="공지사항 제목으로 검색한 결과를 반환합니다.")
	@GetMapping(value ="/searchlist/{title}")
	public ResponseEntity<List<NoticeDto>> searchlist(@PathVariable("title") @ApiParam(value="검색할 게시물이름") String title){
		logger.info("search called... => search:"+title);
		
		List<NoticeDto> list = noticeService.searchlist(title);
		System.out.println(list.size());
		return new ResponseEntity(list,HttpStatus.OK);		
	}

}
