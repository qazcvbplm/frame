package sunwou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.App;
import sunwou.util.FileUtil;
import sunwou.util.ResultUtil;
import sunwou.valueobject.FileUpObject;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("file")
@Api(value="文件操作模块")
public class FileController {

	@PostMapping(value="up",headers="content-type=multipart/form-data",consumes="application/x-www-form-urlencoded")
	@ApiOperation(value = "文件上传",httpMethod="POST",notes="type可以为image,doc,video,music",
	response=ResponseObject.class)
	public void fileup(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated FileUpObject fileUpObject,BindingResult result){
		if(result.hasErrors())
		{
			new ResultUtil().error(response, request, result.getAllErrors().get(0).getDefaultMessage());
		}
		String rs=FileUtil.fileup("upload/woju", request, fileUpObject.getType(),
				fileUpObject.getFile(), fileUpObject.isCompress(), fileUpObject.getCompressd());
		new ResultUtil().push("path", rs);
	}
	

}
