package sunwou.controller;

import java.util.ArrayList;
import java.util.Date;
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
import sunwou.entity.FullCut;
import sunwou.entity.OpenTime;
import sunwou.entity.Shop;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ICategoryService;
import sunwou.service.IFullCutService;
import sunwou.service.IOpenTimeService;
import sunwou.service.IShopService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
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
	@Autowired
	private IOpenTimeService iOpenTimeService;
	@Autowired
	private ICategoryService iCategoryService;
	
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
			String query,@RequestParam(defaultValue="false") boolean open)
	{
		          QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		          qo.setTableName(MongoBaseDaoImple.SHOP);
		          int count=iShopService.count(qo);
		          List<Shop> rs=iShopService.find(qo);
		          if(open){
		        	  checkOpen(rs);
		          }
		          new ResultUtil().push("shops", rs).push("total",count).out(request, response);
	}
	
	
	
	@PostMapping(value="findById")
	@ApiOperation(value = "查询单个商店",httpMethod="POST",response=ResponseObject.class)
	public void findById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String sunwouId)
	{
		         Shop s=iShopService.findById(sunwouId);
		         if(s==null){
		        	 throw new MyException("商店不存在");
		         }
		         List<Shop> shops=new ArrayList<>();
		         shops.add(s);
		         checkOpen(shops);
		         List<FullCut> fullcuts=iFullCutService.findByShopId(sunwouId);
		         List<OpenTime> opentimes=iOpenTimeService.findByShopId(sunwouId);
                 new ResultUtil().push("shop", shops.get(0)).push("fullcuts", fullcuts).push("opentimes", opentimes).out(request, response);;
	}
	
	
	
	/**
	 * 判断商店是否营业
	 * @param rs
	 */
	private void checkOpen(List<Shop> rs) {
		List<OpenTime> temp;
		long today=new Date().getTime()-TimeUtil.parse(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)+" 00:00:00", TimeUtil.TO_S).getTime();
		for(Shop s:rs){
			if(s.getOpen()){
				temp =iOpenTimeService.findByShopId(s.getSunwouId());
				if(temp.size()>0){
					s.setOpen(false);
					for(OpenTime ot:temp){
						if(today>=ot.getStartL()&&today<ot.getEndL()){
							s.setOpen(true);
							break;
						}
					}
				}
			}
		}
	}


	@PostMapping(value="fullcut/add")
	@ApiOperation(value = "增加满减优惠",httpMethod="POST",response=ResponseObject.class)
	public void fullcutadd(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated FullCut fullCut,BindingResult result)
	{
		Util.checkParams(result);
		iFullCutService.add(fullCut);
		new ResultUtil().success(request, response, "添加成功");
	}
	
	
	@PostMapping(value="fullcut/find")
	@ApiOperation(value = "按店铺查询满减优惠",httpMethod="POST",response=ResponseObject.class)
	public void fullcutfind(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String shopId)
	{
		   List<FullCut> rs=iFullCutService.findByShopId(shopId);
		   new ResultUtil().push("fullcuts", rs).out(request, response);
	}
	
	
	@PostMapping(value="fullcut/update")
	@ApiOperation(value = "更新满减优惠",httpMethod="POST",response=ResponseObject.class)
	public void fullcutupdate(HttpServletRequest request,HttpServletResponse response,
			FullCut fullCut)
	{
			if(iFullCutService.update(fullCut)==1){
				new ResultUtil().success(request, response, "更新成功");
			}else{
				new ResultUtil().error(request, response, "更新失败请重试");
			}
	}
	
	
	@PostMapping(value="opentime/add")
	@ApiOperation(value = "增加营业时间段",httpMethod="POST",response=ResponseObject.class)
	public void opentimeadd(HttpServletRequest request,HttpServletResponse response,
			OpenTime openTime)
	{
		iOpenTimeService.add(openTime);
		new ResultUtil().success(request, response, "添加成功");
	}
	
	
	@PostMapping(value="opentime/find")
	@ApiOperation(value = "按店铺查询营业时间段",httpMethod="POST",response=ResponseObject.class)
	public void opentimefind(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String shopId)
	{
		   List<OpenTime> rs=iOpenTimeService.findByShopId(shopId);
		   new ResultUtil().push("opentimes", rs).out(request, response);
	}
	
	
	@PostMapping(value="opentime/update")
	@ApiOperation(value = "更新营业时间",httpMethod="POST",response=ResponseObject.class)
	public void opentimeupdate(HttpServletRequest request,HttpServletResponse response,
			OpenTime openTime)
	{
			if(iOpenTimeService.update(openTime)==1){
				new ResultUtil().success(request, response, "更新成功");
			}else{
				new ResultUtil().error(request, response, "更新失败请重试");
			}
	}
	
}
