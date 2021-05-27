package com.ssafy.happyhouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.config.security.JwtTokenProvider;
import com.ssafy.happyhouse.model.entity.Member;
import com.ssafy.happyhouse.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*",maxAge=3600)
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    
    @PostMapping("/tokentest")
    public String tokentest() {
    	logger.info("-----------tokencheck called------------");
    	return "";
    }
    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> member) {
    	logger.info("join called : "+member.get("email"));
    	if(member.get("email").equals("")||member.get("name").equals("")) {
    		return null;
    	}
        return memberRepository.save(Member.builder()
        		.name(member.get("name"))
                .email(member.get("email"))
                .password(passwordEncoder.encode(member.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getName();
    }
    
    //10자리의 임의의 문자열 만드는 함수
    private String certified_key() {
    	Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;

		do {
			num = random.nextInt(75) + 48;
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char) num);
			} else {
				continue;
			}

		} while (sb.length() < 10);
		return sb.toString();
    }
    
    // 로그인
    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String, String> map) {
    	logger.info("login called : "+map.get("email"));
        Member member = memberRepository.findByEmail(map.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(map.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());//이메일(PK)과 Role로 token 생성
        logger.info("creatToken : "+token);
        logger.info("username : "+member.getUsername());
        Map<String,String> resdata = new HashMap<String, String>();
        resdata.put("jwt", token);
        resdata.put("name",member.getUsername());
        resdata.put("email", member.getEmail());
        resdata.put("code", member.getCode().toString());
        resdata.put("role", member.getRoles().toString());
        return resdata;
    }
}