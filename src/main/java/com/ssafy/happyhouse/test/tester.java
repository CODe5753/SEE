package com.ssafy.happyhouse.test;

import com.ssafy.happyhouse.model.dto.FeedDto;
import com.ssafy.happyhouse.model.dto.FeedMessageDto;
import com.ssafy.happyhouse.util.RSSFeedParser;

public class tester {
	public static void main(String[] args) {
		RSSFeedParser parser = new RSSFeedParser(
                "http://www.renews.co.kr/rss/S1N4.xml");
		FeedDto feed = parser.readFeed();
		System.out.println(feed);
		for (FeedMessageDto message : feed.getMessages()) {
			System.out.println(message.getTitle());
			
		}
	}
}
