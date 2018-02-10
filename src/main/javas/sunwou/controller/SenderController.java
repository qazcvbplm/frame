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
import sunwou.entity.Address;
import sunwou.entity.Sender;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISenderService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("sender")
@Api(value="配送员模块")
public class SenderController {

	
	@Autowired
	private ISenderService iSenderService;
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加申请",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Sender sender,BindingResult result){
		   Util.checkParams(result);
		   sender.setType("配送员申请");
		   sender.setStatus("待审核");
		   if(iSenderService.add(sender)!=null){
			   new ResultUtil().success(request, response, "成功提交申请");
		   }else{
			   new ResultUtil().error(request, response, "提交失败请重试");
		   }
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询配送员",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,
		@RequestParam(defaultValue="")	String query){
		QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		qo.setTableName(MongoBaseDaoImple.SENDER);
		int count =iSenderService.count(qo);
		List<Sender> rs=iSenderService.find(qo);
		new ResultUtil().push("senders", rs).push("total",count).out(request, response);
	}
	
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新配送员",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,Sender sender){
		          if( iSenderService.update(sender)==1){
		        	  new ResultUtil().success(request, response, "更新成功");
		          }else{
		        	  new ResultUtil().error(request, response, "更新失败");
		          }
	}
	
}
