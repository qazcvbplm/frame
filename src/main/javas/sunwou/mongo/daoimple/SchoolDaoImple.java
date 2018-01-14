package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.School;
import sunwou.mongo.dao.ISchoolDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class SchoolDaoImple extends MongoBaseDaoImple<School> implements ISchoolDao{

}
