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
@ApiModel(value = "관심건물 정보",description = "번호, 사용자 코드, 건물번호 ")
public class InterestBuildingDto {
	
	@ApiModelProperty(value = "번호")
	private int id;
	
	@ApiModelProperty(value = "사용자 코드")
	private int member_code;
	
	@ApiModelProperty(value = "건물번호")
	private int building_id;
	
}
