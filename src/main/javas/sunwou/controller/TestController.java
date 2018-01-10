package sunwou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonSyntaxException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.util.Util;
import sunwou.valueobject.QueryObject;
import sunwou.valueobject.ResponseObject;

@Controller
@Api(value="会员信息")
public class TestController {

/*	@Resource
	private IDubboTestService dubboTestService;*/
	
	
	@RequestMapping("test")
	@ApiOperation(value = "分页搜索获取会员列表",httpMethod="POST",
	notes="{fields:['所需要的属性']},sorts:[{value:'排序字段名字',asc:true}],where:[{value:'条件字段名字',opertionType:'条件类型',opertionValue:'条件值'}]"
	,response=ResponseObject.class)
	public void test(HttpServletResponse response,HttpServletRequest request,@RequestParam("query") String query) {
			QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
		return ;
	}
}
