package sunwou.controller;

import java.util.Collections;
import java.util.Comparator;
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
import sunwou.entity.Article;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IArticleService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("article")
@Api(value="文章模块")
public class ArticleController {

	
	@Autowired
	private IArticleService iArticleService;
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加文章",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Article article,BindingResult result){
		   Util.checkParams(result);
		   iArticleService.add(article);
		   new ResultUtil().success(request, response, "添加成功");
	}
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询文章",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query,@RequestParam(defaultValue="false") boolean admin){
		  QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
          List<Article> rs=iArticleService.find(qo,admin);   
          new ResultUtil().push("articles", rs).out(request, response);
	}
	
	@PostMapping(value="findbyid")
	@ApiOperation(value = "按id查询文章",httpMethod="POST",response=ResponseObject.class)
	public void findbyid(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String sunwouId){
          Article rs=iArticleService.findById(sunwouId);
          new ResultUtil().push("article", rs).out(request, response);
	}
	
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新文章",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
			Article article){
		  if( iArticleService.update(article)==1){
			  new ResultUtil().success(request, response, "更新成功");
		  }
	}
}
