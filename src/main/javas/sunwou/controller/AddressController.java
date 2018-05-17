package sunwou.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sunwou.entity.Address;
import sunwou.entity.Floor;
import sunwou.mongo.daoimple.AddressDaoImple;
import sunwou.mongo.daoimple.FloorDaoImple;
import sunwou.service.IAddressService;
import sunwou.util.ResultUtil;
import sunwou.util.Util;
import sunwou.valueobject.AddressParamObejct;
import sunwou.valueobject.ResponseObject;

@Controller
@RequestMapping("address")
@Api(value="收货地址模块")
public class AddressController {

	@Autowired
	private IAddressService iAddressService;
	@Autowired
	private AddressDaoImple addressDaoImple;
	@Autowired
	private FloorDaoImple floorDaoImple;
	
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
	
	
/*	@RequestMapping("test")
	public void test(){
		int bd=0;
		int xz=0;
		int wld=0;
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("isDelete").is(false),Criteria.where("createDate").gte("2018-05-01"));
		List<Address> adds=addressDaoImple.getMongoTemplate().find(new Query(c), addressDaoImple.getCl());
		try {
			for(int i=0;i<adds.size();i++){
				Address addtemp=adds.get(i);
				Floor flootemp=floorDaoImple.findById(addtemp.getFloorId());
				if(flootemp!=null){
					if(!addtemp.getFloorName().equals(flootemp.getName())){
						bd++;
						Floor flrrtemp2=floorDaoImple.getByName(addtemp.getFloorName());
						if(flrrtemp2!=null){
							addtemp.setFloorId(flrrtemp2.getSunwouId());
							xz+=addressDaoImple.updateById(addtemp);
							System.out.println("修正"+bd);
						}
					}else{
						System.out.println(i+"正常");
					}
				}else{
					wld++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bd+"条数据不相等");
		System.out.println(xz+"条修正");
		System.out.println(wld+"条无楼栋");
	}*/
}
