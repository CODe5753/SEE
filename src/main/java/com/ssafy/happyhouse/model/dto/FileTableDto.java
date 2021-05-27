package com.ssafy.happyhouse.model.dto;

public class FileTableDto {
	
	private int id;
	private int board_id;
	private int file_size;
	private String origin_file_name;
	private String save_file_name;
	private String create_date;
	private String update_date;
	
	public FileTableDto() {}

	public FileTableDto(int id, int board_id, int file_size, String origin_file_name, String save_file_name,
			String create_date, String update_date) {
		super();
		this.id = id;
		this.board_id = board_id;
		this.file_size = file_size;
		this.origin_file_name = origin_file_name;
		this.save_file_name = save_file_name;
		this.create_date = create_date;
		this.update_date = update_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public String getOrigin_file_name() {
		return origin_file_name;
	}

	public void setOrigin_file_name(String origin_file_name) {
		this.origin_file_name = origin_file_name;
	}

	public String getSave_file_name() {
		return save_file_name;
	}

	public void setSave_file_name(String save_file_name) {
		this.save_file_name = save_file_name;
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
}
