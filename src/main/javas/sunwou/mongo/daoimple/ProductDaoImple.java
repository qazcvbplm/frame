package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Product;
import sunwou.mongo.dao.IProductDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class ProductDaoImple extends MongoBaseDaoImple<Product> implements IProductDao{

}
