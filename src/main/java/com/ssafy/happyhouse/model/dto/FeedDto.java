package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
 
public class FeedDto {
    /**
     * 타이틀
     */
    private String title;
    
    /**
     * 링크 url
     */
    private String link;
    
    /**
     * 설명
     */
    private String description;
    
    /**
     * 언어
     */
    private String language;
    
    /**
     *  저작권정보
     */
    private String copyright;
    
    /**
     * 일자
     */
    private String pubDate;
 
    private List<FeedMessageDto> entries = new ArrayList<FeedMessageDto>();
 
    public FeedDto(String title, String link, String description, String language, String copyright, String pubDate) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	public List<FeedMessageDto> getMessages() {
            return entries;
    }
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public List<FeedMessageDto> getEntries() {
		return entries;
	}

	public void setEntries(List<FeedMessageDto> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "FeedDto [title=" + title + ", link=" + link + ", description=" + description + ", language=" + language
				+ ", copyright=" + copyright + ", pubDate=" + pubDate + ", entries=" + entries + "]";
	}
 
}
