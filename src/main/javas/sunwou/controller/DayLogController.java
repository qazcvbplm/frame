package sunwou.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Carousel;
import sunwou.entity.DayLog;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IDayLogService;
import sunwou.service.ISchoolService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("daylog")
@Api(value="学校日志")
public class DayLogController {

	@Autowired
	private IDayLogService iDayLogService;

	@PostMapping(value="find")
	@ApiOperation(value = "统计查询",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		  QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
          qo.setTableName(MongoBaseDaoImple.DAYLOG);
          List<DayLog> rs=iDayLogService.find(qo);
          new ResultUtil().push("dayLogs", rs).out(request, response);
	}
	
	
	
}
