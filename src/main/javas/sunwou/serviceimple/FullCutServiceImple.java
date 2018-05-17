package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import sunwou.entity.FullCut;
import sunwou.mongo.daoimple.FullCutDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IFullCutService;

@Component
public class FullCutServiceImple implements IFullCutService{
	
	@Autowired
	private FullCutDaoImple iFullCutDao;

	@Override
	public String add(FullCut fullCut) {
		return iFullCutDao.add(fullCut);
	}

	@Override
	public List<FullCut> findByShopId(String shopId) {
		Criteria c=new Criteria();
		c.and("shopId").is(shopId).and("isDelete").is(false);
		return iFullCutDao.getMongoTemplate().find(new Query(c).with(new Sort(Direction.ASC, "full")),iFullCutDao.getCl());
	}

	@Override
	public int update(FullCut fullCut) {
		// TODO Auto-generated method stub
		return iFullCutDao.updateById(fullCut);
	}
}
