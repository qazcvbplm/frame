package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.ShopApply;
import sunwou.mongo.dao.IShopApplyDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IShopApplyService;

@Component
public class ShopApplyServiceImple implements IShopApplyService{

	@Autowired
	private IShopApplyDao iShopApplyDao;

	@Override
	public String add(ShopApply shopApply) {
		// TODO Auto-generated method stub
		return iShopApplyDao.add(shopApply);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iShopApplyDao.count(qo);
	}

	@Override
	public List<ShopApply> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iShopApplyDao.find(qo);
	}

	@Override
	public int update(ShopApply shopApply) {
		// TODO Auto-generated method stub
		return iShopApplyDao.updateById(shopApply, MongoBaseDaoImple.SHOPAPPLY);
	}

	@Override
	public ShopApply findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iShopApplyDao.findById(sunwouId, MongoBaseDaoImple.SHOPAPPLY);
	}
}
