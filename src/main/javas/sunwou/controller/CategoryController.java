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
import sunwou.entity.Category;
import sunwou.entity.Category.ProductCategory;
import sunwou.entity.Category.ShopCategory;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ICategoryService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("category")
@Api(value="分类模块")
public class CategoryController {

	@Autowired
	private ICategoryService iCategoryService;
	
	@PostMapping(value="addshopcategory")
	@ApiOperation(value = "添加店铺分类",httpMethod="POST",response=ResponseObject.class)
	public void addshopcategory(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated({ShopCategory.class}) Category category,BindingResult result){
		      Util.checkParams(result);
		      category.setType("店铺分类");
		      if(iCategoryService.add(category)!=null){
		      new ResultUtil().success(request, response, "添加成功");
		      }
	}
	
	@PostMapping(value="addproductcategory")
	@ApiOperation(value = "添加商品分类",httpMethod="POST",response=ResponseObject.class)
	public void addproductcategory(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated({ProductCategory.class}) Category category,BindingResult result){
		      Util.checkParams(result);
		      category.setType("商品分类");
		      if(iCategoryService.add(category)!=null){
		      new ResultUtil().success(request, response, "添加成功");
		      }
	}
	
	@PostMapping(value="addsecondcategory")
	@ApiOperation(value = "添加二手分类",httpMethod="POST",response=ResponseObject.class)
	public void addsecondcategory(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated({ProductCategory.class}) Category category,BindingResult result){
		      Util.checkParams(result);
		      category.setType("二手分类");
		      if(iCategoryService.add(category)!=null){
		      new ResultUtil().success(request, response, "添加成功");
		      }
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询分类",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,
		@RequestParam(defaultValue="")	String query){
		QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		List<Category> rs=iCategoryService.find(qo);
		new ResultUtil().push("categorys", rs).out(request, response);
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新分类",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
		Category category){
		  if( iCategoryService.update(category)==1){
			  new ResultUtil().success(request, response, "更新成功");
		  }
	}
	
	
	
}
