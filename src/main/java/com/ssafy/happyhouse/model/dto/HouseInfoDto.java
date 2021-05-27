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
@ApiModel(value = "HouseInfo 정보",description = "고유 번호, 시군구명, 이름, 면적, 계약일, 타입, 층수, 매매(보증금) ,건축년도,주소, 월세")
public class HouseInfoDto {
	
	@ApiModelProperty(value = "고유 번호")
	private int id;
	
	@ApiModelProperty(value = "시군구명")
	private String sigungu;
	
	@ApiModelProperty(value = "이름")
	private String name;
	
	@ApiModelProperty(value = "면적")
	private float area;
	
	@ApiModelProperty(value = "계약일")
	private String contract_date;
	
	@ApiModelProperty(value = "타입")
	private String contract_type;
	
	@ApiModelProperty(value = "층")
	private int floor;
	
	@ApiModelProperty(value = "매매(보증금)")
	private int dealamount;
	
	@ApiModelProperty(value = "건축년도")
	private int buildyear;
	
	@ApiModelProperty(value = "도로명주소")
	private String roadname;
	
	@ApiModelProperty(value = "월세")
	private int monthlyrent;
	
	@ApiModelProperty(value = "lon")
	private float lon;
	
	@ApiModelProperty(value = "lat")
	private float lat;
	
}
