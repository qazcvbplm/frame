package sunwou.controller;


/*@Controller
@RequestMapping("apply")
@Api(value="申请模块")*/
public class ApplyController {
	
	/*@Autowired
	private IApplyService iApplyService;
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加申请",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated Apply apply,BindingResult result){
		   Util.checkParams(result);
		   if(iApplyService.add(apply)!=null){
			   new ResultUtil().success(request, response, "成功提交申请");
		   }else{
			   new ResultUtil().error(request, response, "提交失败请重试");
		   }
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新申请",httpMethod="POST",response=ResponseObject.class)
	public void add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute @Validated ApplyParamsObject apo,BindingResult result){
		   Util.checkParams(result);
		   if(iApplyService.update(apo)==1){
			   new ResultUtil().success(request, response, "更新成功");
		   }
	}*/
}
