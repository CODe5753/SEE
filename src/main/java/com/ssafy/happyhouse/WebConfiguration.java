package com.ssafy.happyhouse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ssafy.happyhouse.interceptor.ConfirmInterceptor;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	@Autowired
	private ConfirmInterceptor confirmInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*경로 수정 필요함!*/
		System.out.println("addInterceptors");
		List<String> list = new ArrayList<>();
//		list.add("/house/**");
		list.add("/notice/.*");
		//list.add("/member/pwmodify");
		//list.add("/member/memberinfo");
		//registry.addInterceptor(confirmInterceptor).addPathPatterns(list);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.maxAge(3600)
				.allowedMethods("*");
	}
}
