package com.ssafy.happyhouse.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MemberRole {
	private Long id;
	private Long memberId;
	private String roleName;	
}
