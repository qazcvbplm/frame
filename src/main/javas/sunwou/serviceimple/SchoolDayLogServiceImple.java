package sunwou.serviceimple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.School;
import sunwou.entity.SchoolDayLog;
import sunwou.mongo.dao.ISchoolDayLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.ISchoolDayLogService;
import sunwou.util.TimeUtil;

@Component
public class SchoolDayLogServiceImple implements ISchoolDayLogService{

	
	@Autowired
	private ISchoolDayLogDao iSchoolDayLogDao;


	@Override
	public void everyDay(List<School> schools) {
		    SchoolDayLog sdl=new SchoolDayLog();
		    sdl.add();
            for(School s:schools){
            	sdl.setSchoolId(s.getSunwouId());
            	iSchoolDayLogDao.add(sdl);
            }
	}


	@Override
	public List<SchoolDayLog> getToDay(String schoolId) {
		String today=TimeUtil.formatDate(new Date(), TimeUtil.TO_DAY);
		Criteria c=new Criteria();
		c.and("createDate").is(today);
		if(schoolId!=null)
			c.and("schoolId").is(schoolId);
		return iSchoolDayLogDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SCHOOLDAYLOG));
	}
}
