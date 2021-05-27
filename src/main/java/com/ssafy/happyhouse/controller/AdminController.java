package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/admin")
@Api("관리자 컨트롤러 API V1")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@ApiOperation(value = "회원 목록 조회", notes="회원의 전체 목록을 반환합니다.")
	@GetMapping("/member")
	public ResponseEntity<List<MemberDto>> memberlist(Model model) {
		System.out.println("목록조회");
		List<MemberDto> list = memberService.memberList();
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	@ApiOperation(value = "회원 등록", notes = "회원 정보를 받아서 DB에 저장.")
	@PostMapping(value = "/member")
	public ResponseEntity<List<MemberDto>> userRegister(@RequestBody @ApiParam(value = "회원 한명의 정보", required = true) Map<String,String> map) {
		System.out.println("회원등록 : "+map.get("email"));	
		map.put("checkpwd",map.get("userpwd"));
		try {
			if(memberService.register(map)) {
				List<MemberDto> list = memberService.memberList();
				return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
			} else {
				System.out.println("실패");
				
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "회원정보검색", notes = "유저 코드에 해당하는 회원 한 명을 반환")
	@GetMapping(value = "/member/{usercode}")
	public ResponseEntity<MemberDto> userInfo(@PathVariable("usercode") @ApiParam(value = "검색 할 회원 유저 코드") String usercode) {
		logger.debug("파라미터 : {}", usercode);
		MemberDto memberDto = memberService.memberinfo(usercode);
		if(memberDto != null)
			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "회원정보수정", notes = "회원의 정보를 수정합니다.")
	@PutMapping(value = "/member")
	public ResponseEntity<List<MemberDto>> userModify(@RequestBody @ApiParam(value = "수정 할 회원정보", required = true) Map<String,String>map,Model model) {
		try {
			if(!memberService.adminUpdate(map)) {
				model.addAttribute("msg","빈 칸을 채워주세요.");
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			List<MemberDto> list = memberService.memberList();
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "회원정보삭제", notes = "회원의 정보를 삭제합니다.")
	@DeleteMapping(value = "/member/{email}")
	public ResponseEntity<List<MemberDto>> userDelete(@PathVariable("email") @ApiParam(value = "삭제 할 유저 이메일") String email) {
		try {
			System.out.println("del : "+email);
			memberService.remove(email);
			List<MemberDto> list = memberService.memberList();
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);			
		}
	}
}
