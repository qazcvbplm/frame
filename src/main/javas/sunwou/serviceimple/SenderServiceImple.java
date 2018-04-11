package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Sender;
import sunwou.entity.User;
import sunwou.mongo.dao.ISenderDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
import sunwou.service.IUserService;
@Component
public class SenderServiceImple implements ISenderService{
	
	@Autowired
	private ISenderDao iSenderDao;
	@Autowired
	private ISchoolService iSchoolService;
	@Autowired
	private IUserService iUserService;

	@Override
	public String add(Sender sender) {
		// TODO Auto-generated method stub
		sender.add();
		return iSenderDao.add(sender);
	}

	@Override
	public List<Sender> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iSenderDao.find(qo);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iSenderDao.count(qo);
	}

	@Override
	public int update(Sender sender) {
		sender.update();
		if(sender.getIsDelete()!=null&&sender.getIsDelete()){
			sender=iSenderDao.findById(sender.getSunwouId(), MongoBaseDaoImple.SENDER);
			User user=iUserService.findById(sender.getUserId());
			user.setSenderFlag(false);
			iUserService.update(user);
			sender.setIsDelete(true);
		}
		return iSenderDao.updateById(sender, MongoBaseDaoImple.SENDER);
	}

	@Override
	public Sender findById(String sunwouId) {
		return iSenderDao.findById(sunwouId, MongoBaseDaoImple.SENDER);
	}

	@Override
	public int money(String senderId, BigDecimal amount, boolean add) {
		Sender sender=findById(senderId);
		if(add){
			sender.setMoney(sender.getMoney().add(amount));
		}else{
			sender.setMoney(sender.getMoney().subtract(amount));
			iSchoolService.money(sender.getSchoolId(), amount, add);
		}
		return iSenderDao.updateById(sender, MongoBaseDaoImple.SENDER);
	}

	@Override
	public List<Sender> findBySchool(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("schoolId").is(schoolId),Criteria.where("isDelete").is(false));
		return iSenderDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SENDER));
	}

	@Override
	public void updateLocation(String senderId, String lng, String lat) {
		Sender sender=iSenderDao.findById(senderId, MongoBaseDaoImple.SENDER);
		sender.setLng(lng);
		sender.setLat(lat);
		update(sender);
	}





}
