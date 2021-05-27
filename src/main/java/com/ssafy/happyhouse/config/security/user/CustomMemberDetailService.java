package com.ssafy.happyhouse.config.security.user;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.entity.Member;
import com.ssafy.happyhouse.model.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomMemberDetailService implements UserDetailsService {
	@Autowired
	private SqlSession sqlsession;
//	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
		System.out.println("findByEmail 호출 : "+email);
		return sqlsession.getMapper(MemberRepository.class).findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

}
