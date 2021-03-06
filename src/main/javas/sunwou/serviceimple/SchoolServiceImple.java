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
import sunwou.mongo.daoimple.SchoolDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IAppService;
import sunwou.service.ISchoolService;
import sunwou.valueobject.SchoolLoginParamObject;

@Component
public class SchoolServiceImple implements ISchoolService{

	@Autowired
	private SchoolDaoImple iSchoolDao;
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
		return iSchoolDao.findById(sunwouId);
	}

	@Override
	public School login(SchoolLoginParamObject spo) {
		School school=new School();
		List<School> schools=iSchoolDao.getMongoTemplate().find(new Query(Criteria.where("isDelete").is(false)
				.and("schoolUserName").is(spo.getUserName())
				.and("schoolPassWord").is(spo.getPassWord())), iSchoolDao.getCl());
		if(schools.size()>0)
		return schools.get(0);
		else
			return null;
	}

	@Override
	public int update(School school) {
		// TODO Auto-generated method stub
		school.update();
		return iSchoolDao.updateById(school);
	}

	@Override
	public List<School> findAll() {
		// TODO Auto-generated method stub
		return iSchoolDao.getMongoTemplate().find(new Query(Criteria.where("isDelete").is(false)), iSchoolDao.getCl());
	}

	@Override
	public int money(String schoolId, BigDecimal amount, boolean add) {
		School school=iSchoolDao.findById(schoolId);
        if(add){
        	school.setMoney(school.getMoney().add(amount));
        }else{
        	school.setMoney(school.getMoney().subtract(amount));
        }
        iAppService.total(amount, add);
		return iSchoolDao.updateById(school);
	}

	@Override
	public int SenderMoney(String schoolId, BigDecimal amount, boolean add) {
		School school=iSchoolDao.findById(schoolId);
        if(add){
        	school.setSenderMoney(school.getSenderMoney().add(amount));
        }else{
        	school.setSenderMoney(school.getSenderMoney().subtract(amount));
        }
		return iSchoolDao.updateById(school);
	}

	@Override
	public School findByPhone(String phone) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("phone").is(phone),Criteria.where("isDelete").is(false));
		List<School> school=iSchoolDao.getMongoTemplate().find(new Query(c), School.class);
		return school.size()>0?school.get(0):null;
	}

	
	
	

}
