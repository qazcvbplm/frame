package sunwou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.mongo.util.QueryObject;

@Controller
@RequestMapping("test")
@Api("参数详解")
public class Note {

	@RequestMapping("query")
	@ApiOperation(value="query详解",httpMethod="POST")
	public void note(@RequestBody QueryObject queryObject){
		
	}
}
