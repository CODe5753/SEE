package com.ssafy.happyhouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssafy.happyhouse.model.dto.MemberDto;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("admin interceptor");
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberinfo");
		if(memberDto==null) {
			request.setAttribute("msg", "로그인이 필요합니다.");
			request.getRequestDispatcher("/").forward(request, response);			
			return false;
		}
		else if(memberDto.getLevel()!=4) {
			request.setAttribute("msg", "권한이 없습니다.");
			request.getRequestDispatcher("/").forward(request, response);			
			return false;			
		}
		return true;
		
	}
}
