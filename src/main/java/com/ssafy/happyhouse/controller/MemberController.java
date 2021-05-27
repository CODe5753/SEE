package com.ssafy.happyhouse.controller;


import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.model.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map, Model model,HttpSession session) {
		System.out.println(map.get("email"));
		try {
			MemberDto memberDto = memberService.login(map);
			if(memberDto != null) {
				session.setAttribute("memberinfo", memberDto);
				//쿠키설정 추가할 것(계정 기억, 아이디 저장 등의 기능)
			}else {
				model.addAttribute("msg","아이디 또는 비밀번호 확인 후 로그인해 주세요.");
			}
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","로그인 중 문제가 발생했습니다.");
			return "error/error";
		}
		return "index";
	}
	
	@RequestMapping("/memberinfo")
	public String memberinfo() {
		//System.out.println("여기들어왔지?");
		return "member/memberinfo";
	}
	
	@RequestMapping("/remove")
	public String remove(HttpSession session,Model model) {
		System.out.println("여기들어와?");
		try {
			MemberDto memberDto = (MemberDto) session.getAttribute("memberinfo");
			memberService.remove(memberDto.getEmail());
			session.invalidate();
			model.addAttribute("msg","정상적으로 탈퇴 되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","회원 탈퇴 중 문제가 발생했습니다.");
			return "error/error";
		}
		return "index";
	}
	
	//회원 정보 수정(username, address)
	@PostMapping("/modify")
	public String memberUpdate(@RequestParam Map<String,String> map,Model model,HttpSession session) {
		System.out.println(map.get("email"));
		System.out.println(map.get("name"));
		System.out.println("여기들어왔지?");
		try {
			if(memberService.memberUpdate(map)) {
				model.addAttribute("msg","회원 정보가 수정되었습니다.");
			}else {
				model.addAttribute("msg","회원 정보 수정 실패");
				return "member/memberinfo";
			}
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","회원 정보 수정 중 문제가 발생했습니다.");
			return "error/error";
		}
		return "index";
	}
	
	//회원 비밀번호 수정(userpwd)
	@PostMapping("/pwmodify")
	public String passUpdate(@RequestParam Map<String,String> map,Model model,HttpSession session) {
		//Map에 담기는 데이터
		//1. inputPwd
		//2. newPwd
		//3. newPwd2 (Check용)
		//4. currentPwd (Session을 통해 확인하기)
		try {
			MemberDto memberDto = (MemberDto) session.getAttribute("memberinfo");
			map.put("originPwd", memberDto.getPassword());
			map.put("email", memberDto.getEmail());
			if(!map.get("originPwd").equals(map.get("originPwd2"))) {
				model.addAttribute("msg","현재 비밀번호가 일치하지 않습니다.");
				return "member/pwdchange";
			}if(!map.get("newPwd").equals(map.get("newPwd2"))) {
				model.addAttribute("msg","비밀번호 확인이 일치하지 않습니다.");
				return "member/pwdchange";
			}if(map.get("originPwd").equals(map.get("newPwd"))) {
				model.addAttribute("msg","이전 비밀번호와 일치할 수 없습니다.");
				return "member/pwdchange";
			}
			if(map.get("originPwd2").equals("")||map.get("newPwd").equals("")||map.get("newPwd2").equals("")) {
				model.addAttribute("msg","빈 칸을 채워주세요");				
				return "member/pwdchange";
			}
			memberService.passUpdate(map);
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","비밀번호 변경 중 문제가 발생했습니다.");
			return "error/error";
		}
		session.invalidate();
		return "index";
	}
	
	@RequestMapping("/pwmodify")
	public String passUpdate() {
		return "/member/pwdchange";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/index";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam Map<String, String> map, Model model) {
		System.out.println(map.get("level"));
		System.out.println(map.get("password"));
		System.out.println(map.get("checkpwd"));
		System.out.println("여기안와?");
		try {
			if(memberService.register(map))
				model.addAttribute("msg","회원 가입 성공");
			else
				model.addAttribute("msg","회원 가입 실패");			
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","계정 생성 중 문제가 발생했습니다.");
			return "error/error";
		}
		return "index";
	}
	
	@RequestMapping("/register")
	public String register() {
		return "/member/register";
	}
	
	@RequestMapping("/pwfind")
	public String mvpwfind() {
		return "/member/pwfind";
	}
	
	@PostMapping("/pwfind")
	public String pwfind(@RequestParam Map<String, String> map, Model model) {
		try {
			MemberDto member = memberService.pwfind(map);
			if(member !=null)
				model.addAttribute("msg","회원님의 비밀번호는"+member.getPassword()+" 입니다.");
			else
				model.addAttribute("msg","회원정보와 일치한 정보가 없습니다. 다시한번 확인해주세요.");			
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("msg","비밀번호 찾기 중 문제가 발생했습니다.");
			return "error/error";
		}
		return "index";
	}
}
