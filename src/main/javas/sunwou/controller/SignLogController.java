package sunwou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.SignLog;
import sunwou.service.ISignLogService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("sign")
@Api(value="签到模块")
public class SignLogController {

	
	@Autowired
	private ISignLogService iSignLogService;
	
	@RequestMapping("sign")
	@ApiOperation(value = "签到",httpMethod="POST",consumes="application/x-www-form-urlencoded",
	response=ResponseObject.class)
	private void sign(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Valid SignLog sign,BindingResult result){
		                 Util.checkParams(result);
		                 iSignLogService.sign(sign);
		                 new ResultUtil().push("signLog", sign).out(request, response);
	}
}
