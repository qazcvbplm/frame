package sunwou.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.School;
import sunwou.service.IOrderService;
import sunwou.service.ISchoolService;
import sunwou.service.IUserService;
import sunwou.util.ResultUtil;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("statistics")
@Api(value="统计")
public class StatisticsController {

	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private IOrderService iOrderService;
	
	@RequestMapping("schoolindex")
	@ApiOperation(value = "学校代理首页统计方法",httpMethod="POST",response=ResponseObject.class)
	public void schoolindex(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="") String schoolId){
		       School school=iSchoolService.findById(schoolId);
		       String userActiveCount =iUserService.activeCount(schoolId)+"";
		       BigDecimal schoolMoney=school.getMoney();
		       String  orderNumber=iOrderService.toDaySchoolOrderCount(schoolId)+"";
		       BigDecimal schoolToDayTransactionMoney=iOrderService.schoolToDayTransactionMoney(schoolId);
		       new ResultUtil().
		       push("userActiveCount", userActiveCount).
		       push("schoolMoney", schoolMoney).
		       push("orderNumber", orderNumber).
		       push("schoolToDayTransactionMoney", schoolToDayTransactionMoney).out(request, response);;
		
	}
}
