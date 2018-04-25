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

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.wx.tobank.WChatToBank;
import com.wx.towallet.WeChatUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.FullCut;
import sunwou.entity.OpenTime;
import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.ICategoryService;
import sunwou.service.IDayLogService;
import sunwou.service.IFullCutService;
import sunwou.service.IOpenTimeService;
import sunwou.service.IOrderService;
import sunwou.service.IProductService;
import sunwou.service.ISchoolService;
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
	@Autowired
	private IDayLogService iDayLogService;
	@Autowired
	private ISchoolService iSchoolService;
	
	public static long lastaccepttime=0;	
	

	
	
	
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
		             }else{
		            	 new ResultUtil().error(request, response, "更新成功");
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
		         if(s==null){
		        	 throw new MyException("商店不存在");
		         }
		         s.findById();
		         List<Shop> shops=new ArrayList<>();
		         shops.add(s);
		         checkOpen(shops);
		         List<FullCut> fullcuts=iFullCutService.findByShopId(sunwouId);
                 new ResultUtil().push("shop", shops.get(0)).push("fullcuts", fullcuts).out(request, response);;
	}
	
	
	
	/**
	 * 判断商店是否营业
	 * @param rs
	 * @return 
	 */
	private void checkOpen(List<Shop> rs) {
		List<OpenTime> temp = null;
		List<Shop> open=new ArrayList<>();
		List<Shop> close=new ArrayList<>();
		long today=new Date().getTime()-TimeUtil.parse(TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY)+" 00:00:00", TimeUtil.TO_S).getTime();
		for(Shop s:rs){
			if(s.getOpen()){
				temp =iOpenTimeService.findByShopId(s.getSunwouId());
				if(temp!=null&&temp.size()>0){
					s.setOpen(false);
					for(OpenTime ot:temp){
						if(today>=ot.getStartL()&&today<ot.getEndL()){
							s.setOpen(true);
							break;
						}
					}
				}
			}
			if(s.getOpen()){
				open.add(s);
			}else{
				s.setOpenTime(temp);
				close.add(s);
			}
		}
		rs.clear();
		rs.addAll(open);
		rs.addAll(close);
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
		        	 new ResultUtil().push("orders", Util.gson.toJson(orders)).out(request, response);
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
		if(System.currentTimeMillis()-lastaccepttime<250){
			    acceptorder(request, response, orderId);
		}else{
			lastaccepttime=System.currentTimeMillis();
			App app=iAppService.find();
			synchronized (orderId) {
				Order order=iOrderService.findById(orderId);
				if(order.getStatus().equals("待接手")){
					if(!WXUtil.checkFK(app.getAppid(), app.getMch_id(), app.getPayKeyWX(), orderId)){
						order.setStatus("异常订单");
						iOrderService.update(order);
						throw new MyException("订单异常");
					}
					order.setStatus("商家已接手");
					synchronized (order.getShopId()) {
						order.setWaterNumber(iOrderService.waternumber(order.getShopId())+1);
					}
					int rs=iOrderService.update(order);
					if(order.getType().equals("堂食订单")){
						//发送模板消息
						if (rs == 1) {
							User user=iUserService.findById(order.getUserId());
							app.sendMS(user,order);
						}
					}
					order=iOrderService.findById(orderId);
					new ResultUtil().push("order", Util.gson.toJson(order)).out(request, response);
				}else{
					new ResultUtil().error(request, response, "已接手");
				}
			}
		}
	}
	
	
	@PostMapping("shoplogin")
	@ApiOperation(value = "商家登录",httpMethod="POST",response=ResponseObject.class)
	public void shoplogin(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ShopLoginParamsObject slpo){
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
	
/*	@PostMapping("location")
	@ApiOperation(value = "商家获取位置",httpMethod="POST",response=ResponseObject.class)
	public void location(HttpServletRequest request,HttpServletResponse response,
			String lat,String lng,String sunwouId){
			Shop s=new Shop();
			s.setSunwouId(sunwouId);
			s.setLat(lat);
			s.setLng(lng);
			iShopService.update(s);
	}*/
	
	@PostMapping("sort")
	@ApiOperation(value = "商店排序",httpMethod="POST",response=ResponseObject.class)
	public void sort(HttpServletRequest request,HttpServletResponse response,
			String sunwouId,String type){
			iShopService.sort(sunwouId,type);
			new ResultUtil().success(request, response, "ok");
	}

	
}
