package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.School;
import sunwou.entity.Shop;
import sunwou.mongo.dao.IShopDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IShopService;
import sunwou.valueobject.ShopLoginParamObject;

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

}
