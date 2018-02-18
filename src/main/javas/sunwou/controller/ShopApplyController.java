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
import sunwou.entity.Sender;
import sunwou.entity.ShopApply;
import sunwou.entity.User;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.IOrderService;
import sunwou.service.IShopApplyService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("shopapply")
@Api(value = "商家申请模块")
public class ShopApplyController {

	@Autowired
	private IShopApplyService iShopApplyService;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IAppService iAppService;
	@Autowired
	private IOrderService iOrderService;

	@PostMapping(value = "add")
	@ApiOperation(value = "添加申请", httpMethod = "POST", response = ResponseObject.class)
	public void add(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute @Validated ShopApply shopApply, BindingResult result) {
		Util.checkParams(result);
		User user = iUserService.findById(shopApply.getUserId());
		shopApply.setGender(user.getGender());
		shopApply.setType("商家申请");
		shopApply.setStatus("待审核");
		if (iShopApplyService.add(shopApply) != null) {
			new ResultUtil().success(request, response, "成功提交申请");
		} else {
			new ResultUtil().error(request, response, "提交失败请重试");
		}
	}

	@PostMapping(value = "find")
	@ApiOperation(value = "查询商家申请", httpMethod = "POST", response = ResponseObject.class)
	public void find(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String query) {
		QueryObject qo = Util.gson.fromJson(query, QueryObject.class);
		qo.setTableName(MongoBaseDaoImple.SHOPAPPLY);
		int count = iShopApplyService.count(qo);
		List<ShopApply> rs = iShopApplyService.find(qo);
		new ResultUtil().push("shopApplys", rs).push("total", count).out(request, response);
	}

	@PostMapping(value = "update")
	@ApiOperation(value = "更新申请", httpMethod = "POST", response = ResponseObject.class)
	public void add(HttpServletRequest request, HttpServletResponse response, ShopApply shopApply,
			@RequestParam(defaultValue = "false") boolean reset) {
		if (reset) {
			shopApply.setStatus("待审核");
		}
		if (iShopApplyService.update(shopApply) == 1) {
			new ResultUtil().success(request, response, "更新成功");
		} else {
			new ResultUtil().error(request, response, "更新失败");
		}
	}

	@PostMapping(value = "pass")
	@ApiOperation(value = "商家审核", httpMethod = "POST", response = ResponseObject.class)
	public void pass(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String sunwouId, @RequestParam(defaultValue = "false") Boolean pass) {
		ShopApply shopApply = iShopApplyService.findById(sunwouId);
		User user = iUserService.findById(shopApply.getUserId());
		if (shopApply.getStatus().equals("待审核")) {
			if (pass) {
				shopApply.setStatus("审核通过");
				user.setShoperFlag(true);
			} else {
				shopApply.setStatus("审核失败");
			}
			int rs = iShopApplyService.update(shopApply);
			if (rs == 1) {
				App app = iAppService.find();
				Map<String, String> map = new HashMap<>();
				map.put("appid", app.getAppid());
				map.put("secert", app.getSecertWX());
				map.put("template_id", "dB0c-w3l9T7TlOUJ59cRlXAr1ZPtcnK-QeQLYAc14gw");
				map.put("touser", user.getOpenid());
				map.put("form_id", shopApply.getFormid());
				map.put("keywordcount", "5");
				map.put("keyword1", TimeUtil.formatDate(new Date(), TimeUtil.TO_S));
				map.put("keyword2", shopApply.getStatus());
				if (shopApply.getGender().equals("男") )
					map.put("keyword3", shopApply.getRealName() + "先生");
				if (shopApply.getGender().equals("女"))
					map.put("keyword3", shopApply.getRealName() + "女士");
				map.put("keyword4", shopApply.getType());
				WXUtil.snedM(map);
				iUserService.update(user);
				new ResultUtil().success(request, response, "操作成功");
			}
		}
	}
}
