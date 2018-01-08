package sunwou.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Test;
import sunwou.service.IDubboTestService;
import sunwou.util.SunwouResponse;

@RestController
@Api(value="会员信息")
public class TestController {

/*	@Resource
	private IDubboTestService dubboTestService;*/
	
	
	@RequestMapping("test")
	@ApiOperation(value = "分页搜索获取会员列表",httpMethod="POST")
	public SunwouResponse test(HttpServletResponse rep,Test test){
	/*	try {
			System.out.println(dubboTestService.test());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		SunwouResponse result=new SunwouResponse();
		result.push("object", new Test());
		 return result;
	}
}
