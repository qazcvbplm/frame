package sunwou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.User;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.CodeToUserParamObejct;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("user")
@Api(value="用户模块")
public class UserController {
	
	@Autowired
	private IUserService iUserService;

	@PostMapping(value="code2user")
	@ApiOperation(value = "获取用户信息",httpMethod="POST",notes="用微信code换取用户信息",
	response=ResponseObject.class)
	public void code2user(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated CodeToUserParamObejct cuo,BindingResult result){
		        Util.checkParams(result);
		        String openid = WXUtil.wxlogin("", "", cuo.getCode());
		        User user=iUserService.findByOpenId(openid);
		        if(user==null){
		         //新用户
		        	iUserService.add(openid);
		        }else {
		         //老用户
		        	new ResultUtil().push("user", user).out(request,response);
		        }
	}
}
