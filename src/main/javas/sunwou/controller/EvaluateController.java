package sunwou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Evaluate;
import sunwou.entity.Floor;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IEvaluateService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
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
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询评论",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		             QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		             List<Evaluate> rs=iEvaluateService.find(qo);
		             new ResultUtil().push("pl", rs).push("total", iEvaluateService.count(qo)).out(request, response);
	}
	
	@PostMapping(value="findByShop")
	@ApiOperation(value = "查询评论",httpMethod="POST",response=ResponseObject.class)
	public void findByShop(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String shopId){
		             List<Evaluate> rs=iEvaluateService.findByShop(shopId);
		             new ResultUtil().push("pl", rs).out(request, response);
	}
}
