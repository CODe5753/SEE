package com.ssafy.happyhouse.model.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.happyhouse.model.entity.Member;
/**
 * token확인후 해당 유저의 정보를 반환한다.*/
@Mapper
public interface MemberRepository extends JpaRepository<Member, Long>{

	Optional<Member> findByEmail(String email);

}
