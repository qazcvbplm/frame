package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.SchoolDayLog;
import sunwou.mongo.dao.ISchoolDayLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class SchoolDayLogDaoImple extends MongoBaseDaoImple<SchoolDayLog> implements ISchoolDayLogDao{

}
	