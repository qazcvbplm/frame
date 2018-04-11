package sunwou.controller;

import java.util.Date;
import java.util.List;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.School;
import sunwou.exception.MyException;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.ISchoolService;
import sunwou.util.ResultUtil;
import sunwou.util.TimeUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.valueobject.SchoolLoginParamObject;
import sunwou.valueobject.WithdrawalsParamsObject;
import sunwou.valueobject.WithdrawwalsObject;

@Controller
@RequestMapping("school")
@Api(value="学校模块")
public class SchoolController {
	
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private IAppService iAppService;
	

	@PostMapping(value="add")
	@ApiOperation(value = "添加一个学校",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated School school,BindingResult result){
		   Util.checkParams(result);
		   if(iSchoolService.add(school)!=null){
			    new ResultUtil().success(request, response, "添加成功");
		   }else{
			   new ResultUtil().error(request, response, "添加失败请重试");
		   }
		
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新学校",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,School school){
		   if(iSchoolService.update(school)==1){
			    new ResultUtil().push("school", iSchoolService.findById(school.getSunwouId())).out(request, response);;
		   }else{
			   new ResultUtil().error(request, response, "更新失败请重试");
		   }
		
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询学校",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		    QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		    qo.setTableName(MongoBaseDaoImple.SCHOOL);
		    List<School> rs=iSchoolService.find(qo);
		    new ResultUtil().push("schools", rs).out(request, response);
	}
	
	
	@PostMapping(value="login")
	@ApiOperation(value = "学校代理登录",httpMethod="POST",response=ResponseObject.class)
	public void find(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute @Validated SchoolLoginParamObject spo,BindingResult result){
		         Util.checkParams(result);
		         School school=iSchoolService.login(spo);
		         if(school==null){
		        	 new ResultUtil().error(request, response, "账号或密码错误");
		         }else{
		        	 new ResultUtil().push("school",school).push("jurisdiction", "代理").out(request, response);
		         }
	}
	
	
	@PostMapping("withdrawals")
	@ApiOperation(value = "学校提现",httpMethod="POST",response=ResponseObject.class)
	public void shopwithdrawals(HttpServletRequest request,HttpServletResponse response,
		@ModelAttribute @Validated	WithdrawalsParamsObject wpo,BindingResult result){
					Util.checkParams(result);
		             CommonController.checkSecert(wpo.getSecert());
		             School school=iSchoolService.findById(wpo.getSchoolId());
		             String payId="tx"+TimeUtil.formatDate(new Date(), TimeUtil.TO_S2);
		             WithdrawwalsObject wo = null;
		             if(wpo.getType().equals("零钱")){
		            	 wo=new WithdrawwalsObject(request, wpo.getSchoolId(), payId,
		            			 wpo.getAmount(), school.getPhone(), wpo.getOpenid(), wpo.getSchoolId(), "代理零钱提现");
		             }if(wpo.getType().equals("银行卡")){
		            	 wo=new WithdrawwalsObject(request, wpo.getSchoolId(), payId, wpo.getAmount(),
		            			 wpo.getName(), wpo.getBankNumber(), wpo.getBankCode(), payId, "代理银行卡提现", "");
		             }
		            	 try {
		            		 if(wo!=null){
		            			 String rs=iAppService.withdrawals(wo);
		            			 if(rs.equals("支付成功")){
		            				 new ResultUtil().success(request, response, rs);
		            			 }else{
		            				 new ResultUtil().error(request, response, rs);
		            			 }
		            		 }
						} catch (Exception e) {
							throw new MyException(e.getMessage());
						}
	}
	
	/**
	 */
	 @Scheduled(cron = "0 0 2 * * ?") //每天凌晨2点执行
	 public void clear(){
		 List<School> schools=iSchoolService.findAll();
		 for(School s:schools){
			 if(s.getIndexTopDay()>0){
				 s.setIndexTopDay(s.getIndexTopDay()-1);
				 iSchoolService.update(s);
			 }
		 }
	 }
	
}
