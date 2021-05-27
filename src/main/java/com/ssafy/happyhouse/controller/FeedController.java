package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.FeedDto;
import com.ssafy.happyhouse.model.dto.FeedMessageDto;
import com.ssafy.happyhouse.model.dto.MemberDto;
import com.ssafy.happyhouse.util.RSSFeedParser;

import io.swagger.annotations.Api;
import io.swagger.models.Model;

@RestController
@Api("크롤링 컨트롤러")
@RequestMapping("/crawler")
@CrossOrigin("*")
public class FeedController {
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	RSSFeedParser parser = new RSSFeedParser("http://www.renews.co.kr/rss/S1N4.xml");
	@GetMapping("/getlist")
	public ResponseEntity<List<FeedMessageDto>> getlist(Model model){
		logger.info("RSS 조회");
		FeedDto feed = parser.readFeed();
		List<FeedMessageDto> list = new ArrayList<>();
		for(FeedMessageDto message:feed.getMessages()) {
//			System.out.println(message.getTitle());
			list.add(message);
		}
		logger.info("RSS result data : "+list.size());
		if(list!=null && !list.isEmpty()) {
			return new ResponseEntity<List<FeedMessageDto>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

}

