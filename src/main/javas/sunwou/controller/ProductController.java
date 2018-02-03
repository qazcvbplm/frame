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
import sunwou.entity.Address;
import sunwou.entity.Product;
import sunwou.entity.School;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IProductService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("product")
@Api(value="商品模块")
public class ProductController {

	@Autowired
	private IProductService iProductService;
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加商品",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Product product,BindingResult result){
		   Util.checkParams(result);
		   if(iProductService.add(product)!=null){
			    new ResultUtil().success(request, response, "添加成功");
		   }else{
			   new ResultUtil().error(request, response, "添加失败请重试");
		   }
		
	}
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询商品",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		    QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		    qo.setTableName(MongoBaseDaoImple.PRODUCT);
		    List<Product> rs=iProductService.find(qo);
		    new ResultUtil().push("products", rs).out(request, response);
	}
	
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新商品",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,Product product){
		          if( iProductService.update(product)==1){
		        	  new ResultUtil().success(request, response, "更新成功");
		          }else{
		        	  new ResultUtil().error(request, response, "更新失败");
		          }
	}
	
}
