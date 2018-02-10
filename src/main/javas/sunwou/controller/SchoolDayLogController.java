package sunwou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import sunwou.entity.School;
import sunwou.service.ISchoolDayLogService;
import sunwou.service.ISchoolService;

@Controller
@RequestMapping("schooldaylog")
@Api(value="学校日志")
public class SchoolDayLogController {

	@Autowired
	private ISchoolDayLogService iSchoolDayLogService;
	@Autowired
	private ISchoolService iSchoolService;
	
}
