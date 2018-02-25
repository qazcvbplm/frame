package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.DayLog;
import sunwou.mongo.dao.IDayLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class DayLogDaoImple extends MongoBaseDaoImple<DayLog> implements IDayLogDao{

}
	