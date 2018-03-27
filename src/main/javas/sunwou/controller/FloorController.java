package sunwou.controller;

import java.util.List;

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
import sunwou.entity.Floor;
import sunwou.entity.School;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IFloorService;
import sunwou.service.ISchoolService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("floor")
@Api(value="楼栋模块")
public class FloorController {
	
	@Autowired
	private IFloorService iFloorService;
	@Autowired
	private ISchoolService iSchoolService;
	
	@PostMapping(value="add")
	@ApiOperation(value = "增加楼栋",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Floor floor,BindingResult result){
		             Util.checkParams(result);
		             School school=iSchoolService.findById(floor.getSchoolId());
		             if(school==null){
		            	 throw new MyException("学校不存在");
		             }else
		             {
		            	 iFloorService.add(floor);
		            	 new ResultUtil().success(request, response, "添加成功");
		             }
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询楼栋",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		             QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		             qo.setTableName(MongoBaseDaoImple.FLOOR);
		             List<Floor> rs=iFloorService.find(qo);
		             new ResultUtil().push("floors", rs).push("total", iFloorService.count(qo)).out(request, response);
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新楼栋",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
		Floor floor){
		  if( iFloorService.update(floor)==1){
			  new ResultUtil().success(request, response, "更新成功");
		  }
	}
	
	
}
