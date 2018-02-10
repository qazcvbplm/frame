package sunwou.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
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
import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IOrderService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
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
										iUserService.addSource(order.getUserId(),order.getTotal().intValue(),"加");
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
	 */
	 @Scheduled(cron = "0 0 2 * * ?") //每天凌晨2点执行
	 public void clear(){
		 iOrderService.clear();
	 }
	
	
}
