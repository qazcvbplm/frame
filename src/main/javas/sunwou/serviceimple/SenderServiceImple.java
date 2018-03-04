package sunwou.serviceimple;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Sender;
import sunwou.mongo.dao.ISenderDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISchoolService;
import sunwou.service.ISenderService;
@Component
public class SenderServiceImple implements ISenderService{
	
	@Autowired
	private ISenderDao iSenderDao;
	@Autowired
	private ISchoolService iSchoolService;
	

	@Override
	public String add(Sender sender) {
		// TODO Auto-generated method stub
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





}
