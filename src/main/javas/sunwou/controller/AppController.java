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
import sunwou.entity.App;
import sunwou.exception.MyException;
import sunwou.mongo.dao.IAppDao;
import sunwou.service.IAppService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("app")
@Api(value="app总体信息模块")
public class AppController {

	@Autowired
	private IAppService iAppService;
	
	@PostMapping("update")
	@ApiOperation(value = "修改app总信息",httpMethod="POST",notes="不存在则为添加",
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
			new ResultUtil().push("app", iAppService.findById(app.getSunwouId())).out(request,response);
			return;
		}
		
	}
	
}
