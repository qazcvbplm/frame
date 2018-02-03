package sunwou.controller;

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
import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;
import sunwou.service.IShopService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.valueobject.ShopLoginParamObject;

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
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新学校",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,School school){
		   if(iSchoolService.update(school)==1){
			    new ResultUtil().success(request, response, "更新成功");
		   }else{
			   new ResultUtil().error(request, response, "更新失败请重试");
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
	
	
	@PostMapping(value="login")
	@ApiOperation(value = "学校代理登录",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated ShopLoginParamObject spo,BindingResult result){
		         Util.checkParams(result);
		         School school=iSchoolService.login(spo);
		         if(school==null){
		        	 new ResultUtil().error(request, response, "账号或密码错误");
		         }else{
		        	 new ResultUtil().push("school",school).push("jurisdiction", "代理").out(request, response);
		         }
	}
	
	
	/**
	 */
	 @Scheduled(cron = "0 0 0 * * ?") //每天凌晨0点执行
	 public void clear(){
		 List<School> schools=iSchoolService.findAll();
		 for(School s:schools){
			 if(s.getIndexTopDay()>0){
				 s.setIndexTopDay(s.getIndexTopDay()-1);
				 iSchoolService.update(s);
			 }
		 }
	 }
}
