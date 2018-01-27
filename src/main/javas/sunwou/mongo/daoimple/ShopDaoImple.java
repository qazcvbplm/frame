package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Shop;
import sunwou.mongo.dao.IShopDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class ShopDaoImple extends MongoBaseDaoImple<Shop> implements IShopDao{

}
