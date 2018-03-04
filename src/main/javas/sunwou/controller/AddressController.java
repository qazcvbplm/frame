package sunwou.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wx.towallet.HttpClientCustomSSL;
import com.wx.towallet.SignTools;
import com.wx.towallet.WeChatConfig;
import com.wx.towallet.WeChatUtil;
import com.wx.towallet.XMLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Address;
import sunwou.service.IAddressService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddressParamObejct;
import sunwou.valueobject.ResponseObject;
import sunwou.websocket.MyWebSocket;

@Controller
@RequestMapping("address")
@Api(value="收货地址模块")
public class AddressController {

	@Autowired
	private IAddressService iAddressService;
	
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加收货地址",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Address address,BindingResult result){
		   Util.checkParams(result);
		   iAddressService.add(address);
		   new ResultUtil().success(request, response, "ok");
	}
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询用户的收货地址",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated AddressParamObejct apo,BindingResult result){
		            Util.checkParams(result);
		            List<Address> rs=iAddressService.find(apo);
		            new ResultUtil().push("address", rs).out(request, response);
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新收货地址",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,Address address){
		          if( iAddressService.update(address)==1){
		        	  new ResultUtil().success(request, response, "更新成功");
		          }else{
		        	  new ResultUtil().error(request, response, "更新失败");
		          }
	}
}
