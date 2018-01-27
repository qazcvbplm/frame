package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.School;
import sunwou.mongo.dao.ISchoolDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;
import sunwou.valueobject.ShopLoginParamObject;

@Component
public class SchoolServiceImple implements ISchoolService{

	@Autowired
	private ISchoolDao iSchoolDao;

	@Override
	public String add(School school) {
		school.add();
		iSchoolDao.add(school);
		return school.getSunwouId();
	}

	@Override
	public List<School> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iSchoolDao.find(qo);
	}

	@Override
	public School findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iSchoolDao.findById(sunwouId, MongoBaseDaoImple.SCHOOL);
	}

	@Override
	public School login(ShopLoginParamObject spo) {
		School school=new School();
		List<School> schools=iSchoolDao.getMongoTemplate().find(new Query(Criteria.where("isDelete").is(false)
				.and("schoolUserName").is(spo.getUserName())
				.and("schoolPassWord").is(spo.getPassWord())), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SCHOOL));
		if(schools.size()>0)
		return schools.get(0);
		else
			return null;
	}
}
