package sunwou.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.qcloudsms.httpclient.HTTPException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.entity.Category;
import sunwou.entity.Shop;
import sunwou.exception.MyException;
import sunwou.service.IAppService;
import sunwou.service.ICategoryService;
import sunwou.service.IShopService;
import sunwou.util.ResultUtil;
import sunwou.util.StringUtil;
import sunwou.util.Util;
import sunwou.valueobject.ResponseObject;
import sunwou.wx.WXUtil;

@Controller
@RequestMapping("common")
@Api(value="公共接口")
public class CommonController {
	
	private static Map<String,String> phoneCode=new HashMap<>();
	private static Map<String,Long> codeTime=new HashMap<>();
	private static Map<String,Long> secertTime=new HashMap<>();
	private static Map<String,String> secertPhone=new HashMap<>();
	private static long CODE_OUTTIME=5*60*1000;
	
	@Autowired
	private IAppService appService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private ICategoryService categoryService;
	
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
	
	public static String checkSecert(String secer){
		 if(!secertTime.containsKey(secer))
			 throw new MyException("登录态失效请重新登录");
		 if(System.currentTimeMillis()-secertTime.get(secer)>CODE_OUTTIME)
			 throw new MyException("登录态失效请重新登录");
		 
		 return secertPhone.get(secer);
	}
	
	
	@RequestMapping("checkcode")
	@ApiOperation(value = "确认验证码",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void checkCode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue="") String phone,@RequestParam(defaultValue="")String code){
		            check(phone, code);
		            String secer=UUID.randomUUID().toString();
		            secertTime.put(secer, System.currentTimeMillis());
		            secertPhone.put(secer, phone);
		            new ResultUtil().push("secert", secer).out(request, response);
	}
	
	
	@RequestMapping("barcode")
	@ApiOperation(value = "获取小程序码",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void barcode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String page,@RequestParam String id){
		if(StringUtil.isEmpty(page)||StringUtil.isEmpty(id)){
			return ;
		}
		if(shopService.findById(id)!=null){
			page+="?shopid="+id;
			Shop shop=shopService.findById(id);
			Category ca= categoryService.findById(shop.getCategoryId());
			if(ca.getName().equals("叮点百货")){
				page+="&lingshi=1";
			}
		}
		App app=appService.find();
		String root=new File(request.getSession().getServletContext().getRealPath("/")).getParent();
		File dir=new File(root+"/upload/barcode");
		if(!dir.exists()){
			dir.mkdirs();
		}
		String path="/upload/barcode/"+id+".jpg";
		File barcode=new File(root+path);
		if(!barcode.exists()){
			WXUtil.getCode(app.getAppid(), app.getSecertWX(), page, root+path);
		}
		new ResultUtil().success(request, response, path);
	}
	
	@RequestMapping("completesendmoney")
	@ApiOperation(value = "计算配送费",httpMethod="POST",notes="",
	response=ResponseObject.class)
	public void completesendmoney(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String floorId,@RequestParam String shopId){
		if(floorId==null||shopId==null){
			throw new MyException("参数错误");
		}else{
			new ResultUtil().push("money", appService.completeSenderMoney(floorId, shopId,null)).out(request, response);
		}
	}
	

}
