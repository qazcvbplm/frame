package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Sender;
import sunwou.mongo.dao.ISenderDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ISenderService;
@Component
public class SenderServiceImple implements ISenderService{
	
	@Autowired
	private ISenderDao iSenderDao;

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

}
