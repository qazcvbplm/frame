package sunwou.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sunwou.wx.WXpayUtil;

@Controller
public class NotifyController {

	
	@RequestMapping("notify")
	public void notify(HttpServletResponse response,HttpServletRequest request) throws IOException{
		          WXpayUtil.notify(request, response);
	}
}
