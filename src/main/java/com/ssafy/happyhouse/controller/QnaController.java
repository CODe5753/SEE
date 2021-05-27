package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.QnaCommentDto;
import com.ssafy.happyhouse.model.dto.QnaDto;
import com.ssafy.happyhouse.model.service.QnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
@Api("QnA 컨트롤러 API V1")
@CrossOrigin(origins="*",maxAge=3600)
public class QnaController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	@Autowired
	private QnaService qnaService;
	
	@ApiOperation(value = "QnA 등록", notes = "QnA게시글 DB에 저장.")
	@PostMapping("/regist")
	public ResponseEntity<List<QnaDto>> regist(@RequestBody @ApiParam(value = "게시글 등록", required = true) QnaDto qnaDto) {
		logger.info("[Called] QnA 등록"+qnaDto.getMember_code()+","+qnaDto.getType());
//		qnaDto.setMember_code(1);
		try {
			if(qnaService.regist(qnaDto)) {
				List<QnaDto> list = qnaService.getlist();
				return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
			} else {
				System.out.println("실패");
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "QnA 목록 조회", notes="QnA 게시판 전체글을 반환합니다.")
	@GetMapping("/getquestion")
	public ResponseEntity<List<QnaDto>> getlist() {
		System.out.println("목록조회");
		logger.info("[Called] QnA 목록조회");
		List<QnaDto> list = qnaService.getlist();
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<QnaDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "QnA게시글 수정", notes ="게시글을 수정합니다.")
	@PutMapping(value = "/update")
	public ResponseEntity<List<QnaDto>> update(@RequestBody @ApiParam(value = "수정 할 게시글 정보") QnaDto qnaDto) {
		logger.info("[Called] QnA게시글 수정");
		qnaService.update(qnaDto);
		List<QnaDto> list = qnaService.getlist();
		return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA게시글 삭제", notes ="게시글을 삭제합니다.")
	@DeleteMapping(value = "/remove/{id}")
	public ResponseEntity<List<QnaDto>> remove(@PathVariable("id") @ApiParam(value = "삭제 할 게시글 id") int id) {
		logger.info("[Called] QnA게시글 삭제");
		qnaService.remove(id);
		List<QnaDto> list = qnaService.getlist();
		return new ResponseEntity<List<QnaDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA게시글검색", notes ="해당하는 게시글 정보 가져옴")
	@GetMapping(value = "/getquestion/{id}")
	public ResponseEntity<QnaDto> getquestion(@PathVariable("id") @ApiParam(value="검색 할 게시글번호") int id) {
		logger.info("[Called] QnA게시글 검색");
		QnaDto qnaDto = qnaService.getquestion(id);
		if(qnaDto != null)
			return new ResponseEntity<QnaDto>(qnaDto, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Qna 상세 검색", notes="QnA 상세 검색한 결과를 반환합니다.")
	@GetMapping("/getquestion/{keyword}/{selected}")
	public ResponseEntity<List<QnaDto>> search(@PathVariable("keyword") @ApiParam(value="검색할 검색어") String keyword,@PathVariable("selected") @ApiParam(value="선택자") String selected,Model model){
		logger.info("search called... => search:"+keyword+" selected:"+selected);
		Map<String,String> map = new HashMap<String, String>();
		map.put("select", selected);
		map.put("search", keyword);
		
		List<QnaDto> list = qnaService.searchlist(map);		
		return new ResponseEntity(list,HttpStatus.OK);		
	}
	
	@ApiOperation(value="QnA 내 글 가져오기",notes="QnA 게시판에 본인이 작성한 질문글 답글을 모두 반환합니다.")
	@GetMapping("/getmyqna/{id}")
	public ResponseEntity<List<QnaDto>> getmyqna(@PathVariable("id") @ApiParam(value="사용자 코드") String id){
		logger.info("get my QnA called... => id:"+id);
		List<QnaDto> list = qnaService.getmyqna(id);		
		return new ResponseEntity(list,HttpStatus.OK);		
	}
	
	@ApiOperation(value = "QnA 답변 조회", notes="QnA 게시판 전체 답변을 반환합니다.")
	@GetMapping("/getanswer/{id}")
	public ResponseEntity<List<QnaDto>> getanswer(@PathVariable("id") @ApiParam(value="검색 할 게시글번호") int id) {
		logger.info("[Called] QnA답변 조회");
		List<QnaDto> list = qnaService.getanswer(id);
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<QnaDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "QnA 댓글등록", notes = "QnA게시글에 대한 댓글을 DB에 저장.")
	@PostMapping("/registcomment")
	public ResponseEntity<QnaCommentDto> registcomment(@RequestBody @ApiParam(value = "해당게시글에 대한 댓글 등록", required = true) QnaCommentDto qnacommentDto) {
		logger.info("[Called] QnA 댓글등록 :"+qnacommentDto.getMember_code());
//		qnacommentDto.setMember_code(1);
		try {
			if(qnaService.registcomment(qnacommentDto)) {
				return new ResponseEntity(HttpStatus.OK);
			} else {
				System.out.println("실패");
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "QnA게시글에 대한 댓글목록 조회", notes="QnA 해당 게시글에 달린 전체댓글을 반환합니다.")
	@GetMapping("/getcomment/{id}")
	public ResponseEntity<List<QnaCommentDto>> getcommentlist(@PathVariable("id") @ApiParam(value="댓글을 조회할 게시글번호") int id) {
		logger.info("[Called] QnA게시글에 대한 댓글목록 조회");
		List<QnaCommentDto> list = qnaService.getcommentlist(id);
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<QnaCommentDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "QnA게시글에 달린 댓글 수정", notes ="게시글에 달린 댓글을 수정합니다.")
	@PutMapping(value = "/updatecomment")
	public ResponseEntity<List<QnaCommentDto>> updatecomment(@RequestBody @ApiParam(value = "수정 할 댓글 정보") QnaCommentDto qnacommentDto) {
		logger.info("[Called] QnA게시글에 달린 해당 댓글 수정");
		qnaService.updatecomment(qnacommentDto);
		List<QnaCommentDto> list = qnaService.getcommentlist(qnacommentDto.getBoard_id());
		return new ResponseEntity<List<QnaCommentDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA게시글에 달린 댓글 삭제", notes ="게시글에 달린 댓글을 삭제합니다.")
	@DeleteMapping(value = "/removecomment/{id}")
	public ResponseEntity<List<QnaCommentDto>> removecomment(@PathVariable("id") @ApiParam(value = "삭제 할 댓글 id") int id) {
		logger.info("[Called] QnA게시글에 달린 해당 댓글 삭제");
		
		qnaService.removecomment(id);
//		List<QnaCommentDto> list = qnaService.getcommentlist(qnacommentDto.getBoard_id());
		return new ResponseEntity<List<QnaCommentDto>>(HttpStatus.OK);
	}
	
	
}
