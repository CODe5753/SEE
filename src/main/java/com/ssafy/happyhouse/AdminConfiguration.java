package com.ssafy.happyhouse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ssafy.happyhouse.interceptor.AdminInterceptor;

@Configuration
public class AdminConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	private AdminInterceptor adminInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*경로 수정 필요함!*/
		System.out.println("add Admin Interceptors");
		List<String> list = new ArrayList<>();
		list.add("/admin/**");//관리자페이지
		list.add("/notice/register");//공지작성		
		registry.addInterceptor(adminInterceptor)
			.addPathPatterns(list)
			.excludePathPatterns("/swagger-ui.html**"); //스웨거 쪽은 제외
	}
}
