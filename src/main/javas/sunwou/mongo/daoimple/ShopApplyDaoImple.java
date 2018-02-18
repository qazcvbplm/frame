package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.ShopApply;
import sunwou.mongo.dao.IShopApplyDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class ShopApplyDaoImple extends MongoBaseDaoImple<ShopApply> implements IShopApplyDao{

}
