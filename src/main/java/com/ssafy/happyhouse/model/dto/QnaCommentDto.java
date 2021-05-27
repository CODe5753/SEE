package com.ssafy.happyhouse.model.dto;

public class QnaCommentDto {
	private int id;
	private int member_code;
	private int board_id;
	private String comment;
	
	public QnaCommentDto(int id, int member_code, int board_id, String comment) {
		super();
		this.id = id;
		this.member_code = member_code;
		this.board_id = board_id;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMember_code() {
		return member_code;
	}

	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
