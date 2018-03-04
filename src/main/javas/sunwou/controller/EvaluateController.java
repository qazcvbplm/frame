package sunwou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Evaluate;
import sunwou.service.IEvaluateService;
import sunwou.util.ResultUtil;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("evaluate")
@Api(value="评价模块")
public class EvaluateController {

	
	@Autowired
	private IEvaluateService iEvaluateService;
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "评价",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Evaluate evaluate){
		        iEvaluateService.add(evaluate);
		        new ResultUtil().success(request, response, "评价成功");
	}
}
