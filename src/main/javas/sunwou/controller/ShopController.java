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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Shop;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IFullCutService;
import sunwou.service.IShopService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("shop")
@Api(value="商店模块")
public class ShopController {

	@Autowired
	private IShopService iShopService;
	@Autowired
	private IFullCutService iFullCutService;
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加商店",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated(sunwou.entity.Shop.add.class) Shop shop,BindingResult result)
	{
		     Util.checkParams(result);
		     String id=iShopService.add(shop);
		     new ResultUtil().success(request, response, id);
	}
	
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新商店信息",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
			Shop shop)
	{
		             if(iShopService.update(shop)==1){
		            	 new ResultUtil().success(request, response, "更新成功");
		             }
		            
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询商店",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
			String query)
	{
		          QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		          qo.setTableName(MongoBaseDaoImple.SHOP);
		          int count=iShopService.count(qo);
		          List<Shop> rs=iShopService.find(qo);
		          new ResultUtil().push("shops", rs).push("total",count).out(request, response);
	}
	
}
