package sunwou.controller;

import java.util.List;
import java.util.Map;

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

import com.google.gson.JsonObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IDayLogService;
import sunwou.service.IOrderService;
import sunwou.service.ISchoolService;
import sunwou.service.IShopService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.valueobject.PayParamsObject;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.NotifyImple;
import sunwou.wx.WXpayUtil;

@Controller
@RequestMapping("order")
@Api(value="订单模块")
public class OrderController {

	
	@Autowired
	private IOrderService iOrderService;
	@Autowired
	private IAppService iAppService;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IShopService iShopService;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private IDayLogService iDayLogService;
	
	@PostMapping(value="addtakeout")
	@ApiOperation(value = "添加外卖订单",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated AddTakeOutParamsObject aop,BindingResult result){
		            Util.checkParams(result);
		            App app=iAppService.find();
		            new ResultUtil().push("sunwouId", iOrderService.add(aop,app)).out(request, response);;
		            
	}
	
	@PostMapping(value="pay")
	@ApiOperation(value = "订单支付方法",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated PayParamsObject ppo,BindingResult result){
					Util.checkParams(result); 
					App app=iAppService.find();
		            Order o=iOrderService.findById(ppo.getSunwouId());
		            User user=iUserService.findById(o.getUserId());
		            JsonObject json=new JsonObject();
		            //设置回调函数和附带字段
		            json.addProperty("callback", "shoporder");
		            json.addProperty("attach", "");
		            Object paymsg=WXpayUtil.payrequest(app.getAppid(), app.getMch_id(), 
		            		app.getPayKeyWX(), "蜗居科技-w", o.getSunwouId(),"1",
		            		user.getOpenid(), request.getRemoteAddr(),json, o, new NotifyImple() {
								@Override
								public boolean notifcation(Map<String, String> map) {
									String out_trade_no=map.get("out_trade_no");
									Order order=iOrderService.findById(out_trade_no);
									if(iOrderService.paysuccess(order)==1){
										return true;
									}else{
										return false;
									}
								}
							});
		            iOrderService.update(o);
		            new ResultUtil().push("msg", paymsg).out(request, response);;
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询订单",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String query)
	{
		          QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		          qo.setTableName(MongoBaseDaoImple.ORDER);
		          int count =iOrderService.count(qo);
		          List<Order> rs=iOrderService.find(qo);
		          new ResultUtil().push("total", count).push("orders", rs).out(request, response);;
	}
	
	
	
	
	
	/**
	 * 每天清理未付款的订单
	 */
	@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
	public void clear() {
		iOrderService.clear();
	}
	
	/**
	 * 每天统计商家订单
	 */
	@Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行
	public void tongji() {
		List<School> schools=iSchoolService.findAll();
		String day=TimeUtil.getYesterday();
		DayLog dayLogApp=new DayLog("app", "app", "平台店铺日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(), schoolTemp.getSchoolName(), "学校商铺日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Shop> shops=iShopService.findBySchool(schoolId);
			for(Shop shopTemp:shops){
				DayLog dayLogshop=new DayLog(schoolTemp.getSunwouId(), shopTemp.getShopName(), "商店日志", false,day);
				iOrderService.shopDayLog(day,dayLogshop);
				iDayLogService.add(dayLogshop);
				dayLogschool.addDayLog(dayLogshop);
			}
			iDayLogService.add(dayLogschool);
			dayLogApp.addDayLog(dayLogschool);
		}
		iDayLogService.add(dayLogApp);
	}
	
	@RequestMapping("test")
	public void test(){
		List<School> schools=iSchoolService.findAll();
		String day=TimeUtil.getYesterday();
		DayLog dayLogApp=new DayLog("app", "app", "平台店铺日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(), schoolTemp.getSchoolName(), "学校商铺日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Shop> shops=iShopService.findBySchool(schoolId);
			for(Shop shopTemp:shops){
				DayLog dayLogshop=new DayLog(schoolTemp.getSunwouId(),shopTemp.getSunwouId(), shopTemp.getShopName(), "商店日志", false,day);
				iOrderService.shopDayLog(day,dayLogshop);
				iDayLogService.add(dayLogshop);
				dayLogschool.addDayLog(dayLogshop);
			}
			iDayLogService.add(dayLogschool);
			dayLogApp.addDayLog(dayLogschool);
		}
		iDayLogService.add(dayLogApp);
	}

	
	/**
	 * 堂食订单每隔2个小时则自动完成
	*/
	@Scheduled(cron = "0 0/20 * * * ?") // 堂食订单处理
	public void tangshidingdan() {
		iOrderService.timeOutProcess();
	}
	
}
