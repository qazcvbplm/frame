package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import sunwou.entity.OpenTime;
import sunwou.mongo.dao.IOpenTimeDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IOpenTimeService;

@Component
public class OpenTimeServiceImple implements IOpenTimeService{

	@Autowired
	private IOpenTimeDao iOpenTimeDao;

	@Override
	public String add(OpenTime openTime) {
		// TODO Auto-generated method stub
		openTime.add();
		return iOpenTimeDao.add(openTime);
	}

	@Override
	public List<OpenTime> findByShopId(String shopId) {
		Criteria c=new Criteria();
		c.and("isDelete").is(false).and("shopId").is(shopId);
		return iOpenTimeDao.getMongoTemplate().find(new Query(c).with(new Sort(Direction.ASC, "startL")), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.OPENTIME));
	}

	@Override
	public int update(OpenTime openTime) {
		// TODO Auto-generated method stub
		return iOpenTimeDao.updateById(openTime, MongoBaseDaoImple.OPENTIME);
	}
	
	
}
