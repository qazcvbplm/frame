package sunwou.controller;

import java.math.BigDecimal;
import java.util.Date;
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
import com.wx.refund.RefundUtil;
import com.wx.towallet.WeChatUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IDayLogService;
import sunwou.service.IOrderService;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.service.IShopService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddRunParamsObject;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.valueobject.PayParamsObject;
import sunwou.valueobject.ResponseObject;
import sunwou.websocket.ShopWebSocket;
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
	@Autowired
	private ISenderService iSenderService;
	
	@PostMapping(value="addtakeout")
	@ApiOperation(value = "添加外卖订单",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated AddTakeOutParamsObject aop,BindingResult result){
		            Util.checkParams(result);
		            App app=iAppService.find();
		            new ResultUtil().push("sunwouId", iOrderService.add(aop,app)).out(request, response);;
		            
	}
	
	@PostMapping(value="addrun")
	@ApiOperation(value = "添加跑腿订单",httpMethod="POST",response=ResponseObject.class)
	public void addrun(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated AddRunParamsObject aop,BindingResult result){
		            Util.checkParams(result);
		            App app=iAppService.find();
		            String rs=iOrderService.addRun(aop,app);
		            if(rs!=null){
		            	new ResultUtil().success(request, response, rs);
		            }
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
		            		app.getPayKeyWX(), "蜗居科技-w", o.getSunwouId(),o.getTotal().multiply(new BigDecimal(100)).intValue()+"",
		            		user.getOpenid(), request.getRemoteAddr(),json, o, new NotifyImple() {
								@Override
								public boolean notifcation(Map<String, String> map) {
									String out_trade_no=map.get("out_trade_no");
									Order order=iOrderService.findById(out_trade_no);
									if(iOrderService.paysuccess(order)==1){
										if(order.getType().equals("外卖订单")||order.getType().equals("堂食订单")){
											//将订单发给商家
											if(!sunwou.util.StringUtil.isEmpty(order.getShopId())){
												ShopWebSocket.send(order.getShopId(), Util.gson.toJson(order));
											}
										}
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
	
	
	@PostMapping(value="cancel")
	@ApiOperation(value = "取消订单",httpMethod="POST",response=ResponseObject.class)
	public void cancel(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String orderId)
	{
				  App app=iAppService.find();
		          Order order=iOrderService.findById(orderId);
		          synchronized (orderId) {
		        	  order=iOrderService.findById(orderId);
		        	  if(order.getStatus().equals("待接手")){
		        		  long payTime=TimeUtil.parse(order.getPayTime(), TimeUtil.TO_S).getTime(); 
		        		  if(System.currentTimeMillis()-payTime>15*60*1000){
		        			  String total_fee=WeChatUtil.bigDecimalToPoint(order.getTotal());
		        			  String rs=RefundUtil.wechatRefund1(app,orderId,total_fee, total_fee);
		        			  if(rs.equals("支付成功")){
		        				  iOrderService.cancel(order);
		        				  new ResultUtil().success(request, response, rs);
		        			  }else{
		        				  new ResultUtil().error(request, response, rs);
		        			  }
		        		  }else{
		        			  throw new MyException("至少大于15分钟才能取消");
		        		  }
		        	  }
				}
	}
	
	
	
	
	
	/**
	 * 每天清理未付款的订单
	 */
	@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
	public void clear() {
		iOrderService.clear();
	}
	
	/**
	 * 每天统计
	 */
	@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点执行
	public void tongji() {
		List<School> schools=iSchoolService.findAll();
		String day=TimeUtil.getYesterday();
		//统计外卖店铺的信息
		DayLog dayLogApp=new DayLog("app", "app","app", "平台商铺日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(),schoolTemp.getSunwouId(),schoolTemp.getSchoolName(), "学校商铺日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Shop> shops=iShopService.findBySchool(schoolId);
			for(Shop shopTemp:shops){
				DayLog dayLogshop=new DayLog(schoolTemp.getSunwouId(),shopTemp.getSunwouId(), shopTemp.getShopName(), "商铺日志", false,day);
				iOrderService.shopDayLog(day,dayLogshop);
				iDayLogService.add(dayLogshop);
				dayLogschool.addDayLog(dayLogshop);
			}
			iDayLogService.add(dayLogschool);
			dayLogApp.addDayLog(dayLogschool);
		}
		iDayLogService.add(dayLogApp);
		//统计跑腿信息
		DayLog dayRunLogApp=new DayLog("app", "app","app", "平台跑腿日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(), schoolTemp.getSunwouId(),schoolTemp.getSchoolName(), "学校跑腿日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Sender> sendersTemp=iSenderService.findBySchool(schoolId);
			for(Sender senderTemp:sendersTemp){
				DayLog dayLogsender=new DayLog(schoolTemp.getSunwouId(),senderTemp.getSunwouId(), senderTemp.getRealName(), "配送员跑腿日志", false,day);
				iOrderService.senderDayLog(day,dayLogsender);
				iDayLogService.add(dayLogsender);
				dayLogschool.addRunDayLog(dayLogsender);
			}
			iDayLogService.add(dayLogschool);
			dayLogApp.addRunDayLog(dayLogschool);
		}
		iDayLogService.add(dayLogApp);
		
	}
	/**
	 * 每天统计商家订单
	 */
	
/*	@RequestMapping("test")
	public void test(){
		List<School> schools=iSchoolService.findAll();
		String day=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
		//统计外卖店铺的信息
		DayLog dayLogApp=new DayLog("app", "app","app", "平台商铺日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(),schoolTemp.getSunwouId(),schoolTemp.getSchoolName(), "学校商铺日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Shop> shops=iShopService.findBySchool(schoolId);
			for(Shop shopTemp:shops){
				DayLog dayLogshop=new DayLog(schoolTemp.getSunwouId(),shopTemp.getSunwouId(), shopTemp.getShopName(), "商铺日志", false,day);
				iOrderService.shopDayLog(day,dayLogshop);
				iDayLogService.add(dayLogshop);
				dayLogschool.addDayLog(dayLogshop);
			}
			iDayLogService.add(dayLogschool);
			dayLogApp.addDayLog(dayLogschool);
		}
		iDayLogService.add(dayLogApp);
		//统计跑腿信息
		DayLog dayRunLogApp=new DayLog("app", "app","app", "平台跑腿日志", false,day);
		for(School schoolTemp:schools){
			DayLog dayLogschool=new DayLog(schoolTemp.getSunwouId(), schoolTemp.getSunwouId(),schoolTemp.getSchoolName(), "学校跑腿日志", false,day);
			String schoolId=schoolTemp.getSunwouId();
			List<Sender> sendersTemp=iSenderService.findBySchool(schoolId);
			for(Sender senderTemp:sendersTemp){
				DayLog dayLogsender=new DayLog(schoolTemp.getSunwouId(),senderTemp.getSunwouId(), senderTemp.getRealName(), "配送员跑腿日志", false,day);
				iOrderService.senderDayLog(day,dayLogsender);
				iDayLogService.add(dayLogsender);
				dayLogschool.addRunDayLog(dayLogsender);
			}
			iDayLogService.add(dayLogschool);
			dayRunLogApp.addRunDayLog(dayLogschool);
		}
		iDayLogService.add(dayRunLogApp);
	}*/

	
	/**
	 * 堂食订单每隔2个小时则自动完成
	*/
	@Scheduled(cron = "0 0/20 * * * ?") // 堂食订单处理
	public void tangshidingdan() {
		iOrderService.timeOutProcess();
	}
	
}
