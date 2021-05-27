package com.ssafy.happyhouse.model.dto;

public class InfoDto {
	private int no;
	private String title;
	private String content;
	private String regtime;

	public InfoDto(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public InfoDto(String title, String content, String regtime) {
		super();
		this.title = title;
		this.content = content;
		this.regtime = regtime;
	}

	public InfoDto(int no, String title, String regtime) {
		super();
		this.no = no;
		this.title = title;
		this.regtime = regtime;
	}

	public InfoDto(int no, String title, String content, String regtime) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.regtime = regtime;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

}