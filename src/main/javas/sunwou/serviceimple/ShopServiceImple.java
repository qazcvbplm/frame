package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.mongo.dao.IShopDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IShopService;
import sunwou.valueobject.SchoolLoginParamObject;
import sunwou.valueobject.ShopLoginParamsObject;

@Component
public class ShopServiceImple implements IShopService{

	@Autowired
	private IShopDao iShopDao;

	@Override
	public String add(Shop shop) {
		shop.add();
		iShopDao.add(shop);
		return shop.getSunwouId();
	}

	@Override
	public int update(Shop shop) {
		shop.update();
		return iShopDao.updateById(shop, MongoBaseDaoImple.SHOP);
	}

	@Override
	public List<Shop> find(QueryObject qo) {
		return iShopDao.find(qo);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iShopDao.count(qo);
	}

	@Override
	public Shop findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iShopDao.findById(sunwouId, MongoBaseDaoImple.SHOP);
	}

	@Override
	public Shop login(ShopLoginParamsObject slpo) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("schoolId").is(slpo.getSchoolId()),
				Criteria.where("shopUserName").is(slpo.getShopUserName()),
				Criteria.where("shopPassWord").is(slpo.getShopPassWord()));
		List<Shop> rs=iShopDao.getMongoTemplate().find(new Query(c)	, MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SHOP));
		if(rs.size()>0)
		return rs.get(0);
		else
			return null;
	}

	@Override
	public List<Shop> findBySchool(String schoolId) {
		Criteria c=new Criteria();
		c.andOperator(Criteria.where("schoolId").is(schoolId),Criteria.where("isDelete").is(false));
		return iShopDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.SHOP));
	}


}
