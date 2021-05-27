package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.BaseAddressDto;
import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.service.InterestAreaService;

@RestController
@RequestMapping("/interest")
public class InterestAreaController {
	@Autowired
	private InterestAreaService interestAreaService;
	
	@RequestMapping("/selectAll")
	public List<BaseAddressDto> selectAll(HttpSession session){
		int code = ((MemberDto)session.getAttribute("memberinfo")).getCode();
		List<BaseAddressDto> list = interestAreaService.getInteresetArea(code);
		return list;
	}
	
	@PostMapping("/add/{code}")
	public boolean add(@PathVariable("code") String code,HttpSession session) {
		int membercode = ((MemberDto)session.getAttribute("memberinfo")).getCode();
		if(interestAreaService.addInterest(membercode, code))
			return true;
		else {
			return false;
		}
	}
}
