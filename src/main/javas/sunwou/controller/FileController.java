package sunwou.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import sunwou.util.FileUtil;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.FileUpParamObject;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("file")
@Api(value="文件操作模块")
public class FileController {

	@PostMapping(value="up")
	@ApiOperation(value = "文件上传",httpMethod="POST",notes="type可以为image,doc,video,music",
	response=ResponseObject.class)
	public void fileup(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated FileUpParamObject fileUpObject,BindingResult result){
		Util.checkParams(result);
		String rs=FileUtil.fileup("/upload/woju",request, fileUpObject.getType(),
				fileUpObject.getFile(), fileUpObject.isCompress(), fileUpObject.getCompressd());
		new ResultUtil().push("path", rs).out(request,response);;
	}
	

}
