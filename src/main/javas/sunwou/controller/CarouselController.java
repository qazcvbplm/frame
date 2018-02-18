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
import sunwou.entity.Carousel;
import sunwou.entity.Floor;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ICarouselService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddressParamObejct;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("carousel")
@Api("轮播图")
public class CarouselController {

	@Autowired
	private ICarouselService iCarouselService;
	
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加轮播图",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Carousel carousel,BindingResult result){
		   Util.checkParams(result);
		   iCarouselService.add(carousel);
		   new ResultUtil().success(request, response, "ok");
	}
	
	
	@PostMapping(value="find")
	@ApiOperation(value = "查询轮播图",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue="")String query){
		  QueryObject qo=Util.gson.fromJson(query, QueryObject.class);
          qo.setTableName(MongoBaseDaoImple.CAROUSEL);
          List<Carousel> rs=iCarouselService.find(qo);
          new ResultUtil().push("carousels", rs).out(request, response);
	}
	
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新轮播图",httpMethod="POST",response=ResponseObject.class)
	public void update(HttpServletRequest request,HttpServletResponse response,
			Carousel carousel){
		  if( iCarouselService.update(carousel)==1){
			  new ResultUtil().success(request, response, "更新成功");
		  }
	}
}
