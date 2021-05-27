package com.ssafy.happyhouse.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Notice 정보",description = "공지사항 번호, 제목, 내용, 등록일")
public class NoticeDto {
	
	@ApiModelProperty(value = "공지사항 번호")
	private int no;
	
	@ApiModelProperty(value = "제목")
	private String title;
	
	@ApiModelProperty(value = "내용")
	private String content;
	
	@ApiModelProperty(value = "등록일")
	private String regdate;
}