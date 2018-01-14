package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.School;
import sunwou.mongo.dao.ISchoolDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;

@Component
public class SchoolServiceImple implements ISchoolService{

	@Autowired
	private ISchoolDao iSchoolDao;

	@Override
	public String add(School school) {
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
}
