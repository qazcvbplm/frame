package sunwou.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import sunwou.entity.App;
import sunwou.entity.Category;
import sunwou.entity.FullCut;
import sunwou.entity.OpenTime;
import sunwou.entity.Order;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.ICategoryService;
import sunwou.service.IFullCutService;
import sunwou.service.IOpenTimeService;
import sunwou.service.IOrderService;
import sunwou.service.IProductService;
import sunwou.service.IShopService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.valueobject.ShopLoginParamsObject;
import sunwou.valueobject.ShopStatisticsByTime;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("shop")
@Api(value="商店模块")
public class ShopController {

	@Autowired
	private IUserService iUserService;
	@Autowired
	private IShopService iShopService;
	@Autowired
	private IFullCutService iFullCutService;
	@Autowired
	private IOpenTimeService iOpenTimeService;
	@Autowired
	private ICategoryService iCategoryService;
	@Autowired
	private IProductService iProductService;
	@Autowired
	private IOrderService iOrderService;
	@Autowired
	private IAppService iAppService;
	
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
		          SetFullcutAndMinDiscount(rs);
		          new ResultUtil().push("shops", rs).push("total",count).out(request, response);
	}
	
	
	
	private void SetFullcutAndMinDiscount(List<Shop> rs) {
		for(Shop s:rs){
			s.setFullCut(iFullCutService.findByShopId(s.getSunwouId()));
			s.setMinDiscount(iProductService.minDiscount(s));
		}
	}


	@PostMapping(value="findById")
	@ApiOperation(value = "查询单个商店",httpMethod="POST",response=ResponseObject.class)
	public void findById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String sunwouId)
	{
		         Shop s=iShopService.findById(sunwouId);
		         s.findById();
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
	
	
	
	
	@PostMapping("findorder")
	@ApiOperation(value = "查询待接手订单",httpMethod="POST",response=ResponseObject.class)
	public void findorder(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String sunwouId){
		         Shop shop=iShopService.findById(sunwouId);
		         if(shop!=null){
		        	 List<Order> orders=iOrderService.findByShopDJS(shop);
		        	 new ResultUtil().push("orders", orders).out(request, response);
		         }
	}
	
	
	@PostMapping("findorderToday")
	@ApiOperation(value = "查询全部今天订单",httpMethod="POST",response=ResponseObject.class)
	public void findorderToday(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String sunwouId){
		         Shop shop=iShopService.findById(sunwouId);
		         if(shop!=null){
		        	 List<Order> orders=iOrderService.findorderToday(shop);
		        	 new ResultUtil().push("orders", orders).out(request, response);
		         }
	}
	
	
	@PostMapping("acceptorder")
	@ApiOperation(value = "接手订单",httpMethod="POST",response=ResponseObject.class)
	public void acceptorder(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String orderId){
		        Order order=iOrderService.findById(orderId);
		        order.setStatus("商家已接手");
				 order.setWaterNumber(iOrderService.waternumber()+1);
		        int rs=iOrderService.update(order);
		        if(order.getType().equals("堂食订单")){
		        	//发送模板消息
		        	if (rs == 1) {
						App app = iAppService.find();
						User user=iUserService.findById(order.getUserId());
						Map<String, String> map = new HashMap<>();
						map.put("appid", app.getAppid());
						map.put("secert", app.getSecertWX());
						map.put("template_id", "W_SD2f3TJDEMw5FYLgb9PrZh6cbLFrRWK4kJUg8w5UI");
						map.put("touser", user.getOpenid());
						map.put("form_id", order.getPrepareId());
						map.put("keywordcount", "7");
						map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
						map.put("keyword2", order.getOrderProduct().get(0).getProduct().getName()+"等商品");
						map.put("keyword3", order.getWaterNumber()+"");
						map.put("keyword4", order.getReserveTime());
						map.put("keyword5", order.getShopName());
						map.put("keyword6", order.getShopAddress());
						WXUtil.snedM(map);
					}
		        }
		        new ResultUtil().success(request, response, "接手成功");
	}
	
	
	@PostMapping("shoplogin")
	@ApiOperation(value = "商家登录",httpMethod="POST",response=ResponseObject.class)
	public void shoplogin(HttpServletRequest request,HttpServletResponse response,ShopLoginParamsObject slpo){
		        Shop shop=iShopService.login(slpo);
		        if(shop!=null){
		        	new ResultUtil().success(request, response, shop.getSunwouId());
		        }else{
		        	new ResultUtil().error(request, response, "账号或密码错误");
		        }
	}
	
	
	@PostMapping("statisticsbytimeandshop")
	@ApiOperation(value = "商家按时间统计",httpMethod="POST",response=ResponseObject.class)
	public void statisticsbytimeandshop(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="")String shopId,String startTime,String endTime){
		          ShopStatisticsByTime sbt=new ShopStatisticsByTime();
		          iOrderService.statisticsbytimeandshop(shopId,startTime,endTime,sbt);
		          new ResultUtil().push("result", sbt).out(request, response);
	}
	
}
