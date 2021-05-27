package com.ssafy.happyhouse.model.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "QnA 정보",description = "QnA 게시글 고유 번호, 게시물 분류, 제목, 내용, 작성자 고유코드, QnA게시글 등록일, QnA게시글 수정일을 확인할 수 있음")
public class QnaDto {
	
	@ApiModelProperty(value = "게시글 고유 코드")
	private int id;
	
	@ApiModelProperty(value = "게시글 분류")
	private String type;
	
	@ApiModelProperty(value = "제목")
	private String title;
	
	@ApiModelProperty(value = "내용")
	private String content;
	
	@ApiModelProperty(value = "게시글 고유 코드(답글/댓글 관리용)")
	private int board_id;
	
	@ApiModelProperty(value = "작성자 고유코드")
	private int member_code;
	
	@ApiModelProperty(value = "QnA게시글 등록일")
	private String create_date;
	
	@ApiModelProperty(value = "QnA게시글 수정일")
	private String update_date;
	
	//private List<FileTableDto> fileInfos;
	
	public QnaDto() {}
	
	

	public QnaDto(int id, String type, String title, String content, int board_id, int member_code, String create_date,
			String update_date) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.content = content;
		this.board_id = board_id;
		this.member_code = member_code;
		this.create_date = create_date;
		this.update_date = update_date;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getMember_code() {
		return member_code;
	}

	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

//	public List<FileTableDto> getFileInfos() {
//		return fileInfos;
//	}
//
//	public void setFileInfos(List<FileTableDto> fileInfos) {
//		this.fileInfos = fileInfos;
//	}
	
}
