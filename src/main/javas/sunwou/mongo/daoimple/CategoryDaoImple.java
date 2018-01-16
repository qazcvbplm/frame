package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Category;
import sunwou.mongo.dao.ICategoryDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class CategoryDaoImple extends MongoBaseDaoImple<Category> implements ICategoryDao{

}
