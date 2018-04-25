package sunwou.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.baiduutil.BaiduUtil;
import sunwou.entity.App;
import sunwou.entity.Order;
import sunwou.exception.MyException;
import sunwou.mongo.dao.IAppDao;
import sunwou.mongo.dao.IOrderDao;
import sunwou.service.IAppService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("app")
@Api(value="app总体信息模块")
public class AppController {
	
/*	public static void main(String[] args) {
		WXUtil.checkFK("wx46197a2f1c92edf0", "1480274402", "csB18857818257332522199510208595", "takeout20180423112204-2d");
	}*/

	@Autowired
	private IAppService iAppService;
	@Autowired
	private IOrderDao iorderDao;
	
	@PostMapping("update")
	@ApiOperation(value = "修改app总信息",httpMethod="POST",notes="不存在则为添加",consumes="application/x-www-form-urlencoded",
	response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated App app,BindingResult result){
		if(app.getSunwouId()==null){
			if(iAppService.count(new App())>0){
				throw new MyException("app信息只能存在一条");
			}
			Util.checkParams(result);
			App rs=iAppService.add(app);
			new ResultUtil().push("app", rs).out(request,response);
			return;
		}else{
			iAppService.updateById(app);
			new ResultUtil().push("app", iAppService.find()).out(request,response);
			return;
		}
		
	}
	
	@PostMapping("apk")
	public void update(HttpServletRequest request,HttpServletResponse response){
		new ResultUtil().success(request, response, iAppService.find().getApkVersion());
	}
	

	


}
