package com.ssafy.happyhouse.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.entity.Member;
import com.ssafy.happyhouse.model.service.EmailService;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@GetMapping(value = "/user/email/send")
	public void sendmail(Member user) throws MessagingException {
		StringBuffer emailcontent = new StringBuffer();
		emailcontent.append("<!DOCTYPE html>");
		emailcontent.append("<html>");
		emailcontent.append("<head>");
		emailcontent.append("</head>");
		emailcontent.append("<body>");
		emailcontent.append(
				" <div" 																																																	+ 
				"	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+ 
				"	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+ 
				"		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">YG1110 BLOG</span><br />"																													+ 
				"		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+ 
				"	</h1>\n"																																																+ 
				"	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
				user.getUsername()+																																											
				"		님 안녕하세요.<br />"																																													+ 
				"		YG1110 BLOG에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+ 
				"		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"																													+ 
				"		감사합니다."																																															+ 
				"	</p>"																																																	+ 
				"	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""																																	+
				"	href=\"http://localhost:9999/member/email/certified?=" + user.getEmail() + "&certified=" + user.getCertified() + "\" target=\"_blank\">"														+ 
				"		<p"																																																	+
				"			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"							+ 
				"			메일 인증</p>"																																														+ 
				"	</a>"																																																	+
				"	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
				" </div>"
		);
		
//		emailcontent.append("            <script type='text/javascript' src="//t1.daumcdn.net/tiara/js/v1/tiara.min.js"></script>
//            <script type="text/javascript">window.tiara = {"svcDomain":"user.tistory.com","section":"\uae00\ubdf0","trackPage":"\uae00\ubdf0_\ubcf4\uae30","page":"\uae00\ubdf0","key":"3555662-9","customProps":{"userId":"1365214","blogId":"3555662","role":"user","filterTarget":false,"trackPage":"\uae00\ubdf0_\ubcf4\uae30","entryId":"9"},"entry":{"entryId":"9","categoryName":"Springboot","categoryId":"901002","author":"4150603","authorId":"4150603","image":"4150603","plink":"\/9","tags":[],"key":"3555662-9"},"sentryDsn":"https:\/\/a53520229cd744e798d42900d76b0e2a@aem-collector.daumkakao.io\/713","kakaoAppKey":"b8aef3eeb03fa312b81795386484f051","appUserId":"1550478774"};</script>
//            <script type="text/javascript" src="https://t1.daumcdn.net/tistory_admin/assets/blog/tistory-03b8da90cc12cf7b17ed828fc8e50091dc1204ee/blogs/script/tiara/tiara.min.js?_version_=tistory-03b8da90cc12cf7b17ed828fc8e50091dc1204ee" defer></script>
//                        <script type="text/javascript">
//                window.roosevelt_params_queue = window.roosevelt_params_queue || [{channel_id: 'dk', channel_label: 'tistory'}];
//            </script>
//            <script type="text/javascript" src="//t1.daumcdn.net/midas/rt/dk_bt/roosevelt_dk_bt.js" async></script><script type="text/javascript">if(window.console!=undefined){setTimeout(console.log.bind(console,"%cTISTORY","font:8em Arial;color:#EC6521;font-weight:bold"),0);setTimeout(console.log.bind(console,"%c  나를 표현하는 블로그","font:2em sans-serif;color:#333;"),0);}</script><iframe style="position:absolute;width:1px;height:1px;left:-100px;top:-100px" src="//yg1110.tistory.com/api" id="editEntry"></iframe>            <div class="#menubar menu_toolbar ">
//                                            <h2 class="screen_out">티스토리툴바</h2>
//                                            
//                                       </div>
//                                       <div class="#menubar menu_toolbar "><div class='btn_tool btn_tool_type1' id='menubar_wrapper'></div></div>
//                                       <script type="text/javascript" src="https://t1.daumcdn.net/tistory_admin/assets/blog/tistory-03b8da90cc12cf7b17ed828fc8e50091dc1204ee/blogs/script/menubar.min.js?_version_=tistory-03b8da90cc12cf7b17ed828fc8e50091dc1204ee"></script>        <div class="layer_tooltip">
//            <div class="inner_layer_tooltip">
//                <p class="desc_g"></p>
//            </div>
//        </div>
//        <div id="tistoryEtcLayer" class="layer_post"></div><div id="tistorySnsLayer" class="layer_post"></div></body>");
		emailcontent.append("</html>");
		emailService.sendMail(user.getUsername(), "[집을보다:SEE 이메일 인증]", emailcontent.toString());
	}
}
