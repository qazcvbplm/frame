package sunwou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.School;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("school")
@Api(value="学校模块")
public class SchoolController {
	
	@Autowired
	private ISchoolService iSchoolService;

	@PostMapping(value="add")
	@ApiOperation(value = "添加一个学校",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated School school,BindingResult result){
		   Util.checkParams(result);
		   if(iSchoolService.add(school)!=null){
			    new ResultUtil().success(request, response, "添加成功");
		   }else{
			   new ResultUtil().error(request, response, "添加失败请重试");
		   }
		
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询学校",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		    QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		    qo.setTableName(MongoBaseDaoImple.SCHOOL);
		    List<School> rs=iSchoolService.find(qo);
		    new ResultUtil().push("schools", rs).out(request, response);
	}
}