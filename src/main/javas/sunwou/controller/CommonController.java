package sunwou.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.qcloudsms.httpclient.HTTPException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.exception.MyException;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("common")
@Api(value="公共接口")
public class CommonController {
	
	private static Map<String,String> phoneCode=new HashMap<>();
	private static Map<String,Long> codeTime=new HashMap<>();
	private static Map<String,Long> secertTime=new HashMap<>();
	
	private static long CODE_OUTTIME=5*60*1000;
	@RequestMapping("getcode")
	@ApiOperation(value = "获取验证码",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void code(HttpServletRequest request,HttpServletResponse response,String phone) throws JSONException, HTTPException, IOException{
		StringBuilder code=new StringBuilder();
		for(int i=0;i<4;i++){
			code.append((int)(Math.random()*9));
		}
		Util.qqsms(1400069674, "7089ae8c9a950999c776783aa0d64d67", phone, 89383, code.toString(),null);
		phoneCode.put(phone, code.toString());
		codeTime.put(phone, System.currentTimeMillis());
		new ResultUtil().success(request, response, "ok");
	}
	

	public static void check(String phone,String code){
		  if(!phoneCode.containsKey(phone))
			  throw new MyException("验证码不正确");
		  if(!phoneCode.get(phone).equals(code))
			  throw new MyException("验证码不正确");
          if(System.currentTimeMillis()-codeTime.get(phone)>CODE_OUTTIME){
        	  phoneCode.remove(phone);
        	  codeTime.remove(phone);
        	  throw new MyException("验证码过期");
          }else{
        	  phoneCode.remove(phone);
        	  codeTime.remove(phone);
          }
		
	}
	
	public static void checkSecert(String secer){
		 if(!secertTime.containsKey(secer))
			 throw new MyException("登录态失效请重新登录");
		 if(System.currentTimeMillis()-secertTime.get(secer)>CODE_OUTTIME)
			 throw new MyException("登录态失效请重新登录");
	}
	
	
	@RequestMapping("checkcode")
	@ApiOperation(value = "确认验证码",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void checkCode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String phone,@RequestParam(defaultValue="")String code){
		            check(phone, code);
		            String secer=UUID.randomUUID().toString();
		            secertTime.put(secer, System.currentTimeMillis());
		            new ResultUtil().push("secert", secer).out(request, response);
	}
	

}
