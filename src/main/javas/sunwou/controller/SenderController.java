package sunwou.controller;

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
import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.entity.Sender;
import sunwou.entity.User;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IOrderService;
import sunwou.service.IProductService;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.valueobject.SenderAcceptParamsObject;
import sunwou.valueobject.SenderStatisticsByTime;
import sunwou.valueobject.WithdrawwalsObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("sender")
@Api(value = "配送员模块")
public class SenderController {

	@Autowired
	private ISenderService iSenderService;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IAppService iAppService;
	@Autowired
	private IOrderService iOrderService;
	@Autowired
	private IProductService iProductService;
	@Autowired
	private ISchoolService iSchoolService;


	@PostMapping("findorder")
	@ApiOperation(value = "查询待接手订单", httpMethod = "POST", response = ResponseObject.class)
	public void findorder(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String sunwouId) {
		Sender sender = iSenderService.findById(sunwouId);
		if (sender != null) {
			List<Order> orders = iOrderService.findBySenderDJS(sender);
			new ResultUtil().push("orders", orders).out(request, response);
		}
	}
	
	
	@PostMapping("findorderToday")
	@ApiOperation(value = "查询全部今天订单", httpMethod = "POST", response = ResponseObject.class)
	public void findorderToday(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String sunwouId) {
		Sender sender = iSenderService.findById(sunwouId);
		if (sender != null) {
			List<Order> orders = iOrderService.findorderToday(sender);
			new ResultUtil().push("orders", orders).out(request, response);
		}
	}

	@PostMapping(value = "add")
	@ApiOperation(value = "添加申请", httpMethod = "POST", response = ResponseObject.class)
	public void add(HttpServletRequest request, HttpServletResponse response, @ModelAttribute @Validated Sender sender,
			BindingResult result) {
		Util.checkParams(result);
		User user = iUserService.findById(sender.getUserId());
		sender.setGender(user.getGender());
		sender.setType("配送员申请");
		sender.setStatus("待审核");
		if (iSenderService.add(sender) != null) {
			new ResultUtil().success(request, response, "成功提交申请");
		} else {
			new ResultUtil().error(request, response, "提交失败请重试");
		}
	}

	@PostMapping(value = "find")
	@ApiOperation(value = "查询配送员", httpMethod = "POST", response = ResponseObject.class)
	public void find(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String query) {
		QueryObject qo = Util.gson.fromJson(query, QueryObject.class);
		qo.setTableName(MongoBaseDaoImple.SENDER);
		int count = iSenderService.count(qo);
		List<Sender> rs = iSenderService.find(qo);
		new ResultUtil().push("senders", rs).push("total", count).out(request, response);
	}

	@PostMapping(value = "update")
	@ApiOperation(value = "更新配送员", httpMethod = "POST", response = ResponseObject.class)
	public void add(HttpServletRequest request, HttpServletResponse response, Sender sender,
			@RequestParam(defaultValue = "false") boolean reset) {
		if (reset) {
			sender.setStatus("待审核");
		}
		if (iSenderService.update(sender) == 1) {
			new ResultUtil().success(request, response, "更新成功");
		} else {
			new ResultUtil().error(request, response, "更新失败");
		}
	}

	@PostMapping(value = "pass")
	@ApiOperation(value = "配送员审核", httpMethod = "POST", response = ResponseObject.class)
	public void pass(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String sunwouId, @RequestParam(defaultValue = "false") Boolean pass) {
		Sender sender = iSenderService.findById(sunwouId);
		User user = iUserService.findById(sender.getUserId());
		if (sender.getStatus().equals("待审核")) {
			if (pass) {
				sender.setStatus("审核通过");
				user.setSenderFlag(true);
				if(sender.getRate()==null||sender.getFloorsId()==null||sender.getShops()==null||sender.getShopsId()==null){
					throw new MyException("请先分配楼栋和抽成");
				}
			} else {
				sender.setStatus("审核失败");
			}
			int rs = iSenderService.update(sender);
			if (rs == 1) {
				App app = iAppService.find();
				Map<String, String> map = new HashMap<>();
				map.put("appid", app.getAppid());
				map.put("secert", app.getSecertWX());
				map.put("template_id", "dB0c-w3l9T7TlOUJ59cRlXAr1ZPtcnK-QeQLYAc14gw");
				map.put("touser", user.getOpenid());
				map.put("form_id", sender.getFormid());
				map.put("keywordcount", "4");
				map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
				map.put("keyword2", sender.getStatus());
				if (sender.getGender().equals("男") )
					map.put("keyword3", sender.getRealName() + "先生");
				if (sender.getGender().equals("女"))
					map.put("keyword3", sender.getRealName() + "女士");
				map.put("keyword4", sender.getType());
				WXUtil.snedM(map);
				iUserService.update(user);
				new ResultUtil().success(request, response, "操作成功");
			}
		}
	}

	@PostMapping("acceptorder")
	@ApiOperation(value = "接手订单", httpMethod = "POST", response = ResponseObject.class)
	public void acceptorder(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute @Validated SenderAcceptParamsObject sapo, BindingResult result) {
		Util.checkParams(result);
		Order order = iOrderService.findById(sapo.getOrderId());
		User user=iUserService.findById(order.getUserId());
		App app=iAppService.find();
		Sender sender = iSenderService.findById(sapo.getSenderId());
		if (order.getSenderId() == null) {
			if (iOrderService.senderAcceptOrder(order, sender,app,user) == 1) {
				new ResultUtil().success(request, response, "接手成功");
			}
		} else {
			throw new MyException("订单已被接手");
		}
	}

	@PostMapping("finshorder")
	@ApiOperation(value = "送达订单", httpMethod = "POST", response = ResponseObject.class)
	public void finshorder(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String orderId) {
		Order order = iOrderService.findById(orderId);
		if(order.getStatus().equals("配送员已接手")){
			order.setStatus("已完成");
			if(iOrderService.update(order)==1){
                iOrderService.takeOutComplete(order);
			}
			new ResultUtil().success(request, response, "成功");
		}
	}
	
	
	
	@PostMapping("statisticsbytimeandsender")
	@ApiOperation(value = "配送员按时间统计",httpMethod="POST",response=ResponseObject.class)
	public void statisticsbytimeandsender(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="")String senderId,String startTime,String endTime){
		         SenderStatisticsByTime sbt=new SenderStatisticsByTime();
		         iOrderService.statisticsbytimeandsender(senderId,startTime,endTime,sbt);
		         new ResultUtil().push("result", sbt).out(request, response);
	}
	
	

	@PostMapping("withdrawals")
	@ApiOperation(value = "配送员提现",httpMethod="POST",response=ResponseObject.class)
	public void withdrawals(HttpServletRequest request,HttpServletResponse response,
			String senderId){
		         Sender sender=iSenderService.findById(senderId);
		         User user=iUserService.findById(sender.getUserId());
		         String payId="tx"+TimeUtil.formatDate(new Date(), TimeUtil.TO_S2);
		         School school=iSchoolService.findById(sender.getSchoolId());
		         WithdrawwalsObject wo=new WithdrawwalsObject(request, sender.getSchoolId(),payId, sender.getMoney(),
		        		 sender.getPhone(), user.getOpenid(),sender.getSunwouId(),"配送员提现");
		         try {
					String rs=iAppService.withdrawals(wo);
					new ResultUtil().success(request, response, rs);
				} catch (Exception e) {
					throw new MyException(e.getMessage());
				}
	}
}
