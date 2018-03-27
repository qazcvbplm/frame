package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import sunwou.entity.Order;
import sunwou.entity.School;
import sunwou.mongo.dao.ISchoolDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.ISchoolService;
import sunwou.valueobject.SchoolLoginParamObject;

@Component
public class SchoolServiceImple implements ISchoolService{

	@Autowired
	private ISchoolDao iSchoolDao;
	@Autowired
	private IAppService iAppService;

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
	public School login(SchoolLoginParamObject spo) {
		School school=new School();
		List<School> schools=iSchoolDao.getMongoTemplate().find(new Query(Criteria.where("isDelete").is(false)
				.and("schoolUserName").is(spo.getUserName())
				.and("schoolPassWord").is(spo.getPassWord())), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SCHOOL));
		if(schools.size()>0)
		return schools.get(0);
		else
			return null;
	}

	@Override
	public int update(School school) {
		// TODO Auto-generated method stub
		school.update();
		return iSchoolDao.updateById(school, MongoBaseDaoImple.SCHOOL);
	}

	@Override
	public List<School> findAll() {
		// TODO Auto-generated method stub
		return iSchoolDao.getMongoTemplate().find(new Query(Criteria.where("isDelete").is(false)), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SCHOOL));
	}

	@Override
	public int money(String schoolId, BigDecimal amount, boolean add) {
		School school=iSchoolDao.findById(schoolId, MongoBaseDaoImple.SCHOOL);
        if(add){
        	school.setMoney(school.getMoney().add(amount));
        }else{
        	school.setMoney(school.getMoney().subtract(amount));
        }
        iAppService.total(amount, add);
		return iSchoolDao.updateById(school, MongoBaseDaoImple.SCHOOL);
	}

	@Override
	public int SenderMoney(String schoolId, BigDecimal amount, boolean add) {
		School school=iSchoolDao.findById(schoolId, MongoBaseDaoImple.SCHOOL);
        if(add){
        	school.setSenderMoney(school.getSenderMoney().add(amount));
        }else{
        	school.setSenderMoney(school.getSenderMoney().subtract(amount));
        }
		return iSchoolDao.updateById(school, MongoBaseDaoImple.SCHOOL);
	}

	
	
	

}
