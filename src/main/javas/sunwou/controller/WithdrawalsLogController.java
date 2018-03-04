package sunwou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.WithdrawalsLog;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IWithdrawalsLogService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@Api(value="提现日志")
@RequestMapping("withdrawalslog")
public class WithdrawalsLogController {

	@Autowired
	private IWithdrawalsLogService iWithdrawalsLogService;
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询日志",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		  QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
          qo.setTableName(MongoBaseDaoImple.WITHDRAWALSLOG);
          List<WithdrawalsLog> rs=iWithdrawalsLogService.find(qo);
          new ResultUtil().push("logs", rs).out(request, response);
	}
	
}
