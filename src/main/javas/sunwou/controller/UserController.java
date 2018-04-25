package sunwou.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.StringUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.BindCodeParamObject;
import sunwou.valueobject.CodeToUserParamObejct;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("user")
@Api(value="用户模块")
public class UserController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IAppService iAppService;
	
	

	@PostMapping(value="code2user")
	@ApiOperation(value = "获取用户信息",httpMethod="POST",notes="用微信code换取用户信息",
	response=ResponseObject.class)
	public void code2user(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated CodeToUserParamObejct cuo,BindingResult result){
		        Util.checkParams(result);
		        App app=iAppService.find();
		        String openid = WXUtil.wxlogin(
		        		app.getAppid()==null?"":app.getAppid(), app.getSecertWX()==null?"":app.getSecertWX(), cuo.getCode());
		        User user=iUserService.findByOpenId(openid);
		        if(user==null){
		         //新用户
		        	user=iUserService.add(openid);
		        	new ResultUtil().push("user", user).out(request,response);
		        }else {
		         //老用户
		         //获取今天的日期
		        	String day=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
		         //判断活跃时间
		        	if(!user.getLastLoginTime().equals(day)){
		        		   user.setLastLoginTime(day);
		        		   iUserService.update(user);
		        		   user=iUserService.findByOpenId(openid);
		        	}
		        	new ResultUtil().push("user", user).out(request,response);
		        }
	}
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "获取用户列表",httpMethod="POST",response=ResponseObject.class)
	public void finduser(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		     QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		     qo.setTableName(MongoBaseDaoImple.USER);
		     int count=iUserService.count(qo);
		     List<User> rs=iUserService.findUser(qo);
		     new ResultUtil().push("users", rs).push("total", count).out(request, response);
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新用户信息",httpMethod="POST",response=ResponseObject.class)
	public void updateuser(HttpServletRequest request,HttpServletResponse response,@ModelAttribute User user){
		String sunwouId=user.getSunwouId();
		if(StringUtil.isEmpty(sunwouId)||iUserService.findById(sunwouId)==null){
			throw new MyException("该用户不存在");
		}else{
			//防止更新敏感信息
			user.update();
			//更新用户
			iUserService.update(user);
			new ResultUtil().push("user", iUserService.findById(sunwouId)).out(request, response);;
		}
		
	}
	
	
	@RequestMapping("bindcode")
	@ApiOperation(value = "绑定验证码",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void bindcode(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated BindCodeParamObject bpo,BindingResult result){
		                Util.checkParams(result);
		                CommonController.check(bpo.getPhone(), bpo.getCode());
		                User u=new User();
		                u.setSunwouId(bpo.getUserId());
		                u.setPhone(bpo.getPhone());
		                if(iUserService.update(u)==1){
		                	new ResultUtil().push("user", iUserService.findById(bpo.getUserId())).out(request, response);
		                }
	}
	
	/*
	 * 星期天发放余额
	 */
	@Scheduled(cron ="0 0 9 ? * SUN")
	public void fk(){
		iUserService.detailMoney(null);
	}
	
}
