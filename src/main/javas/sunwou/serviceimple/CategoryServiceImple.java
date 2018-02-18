package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.Category;
import sunwou.mongo.dao.ICategoryDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.ICategoryService;

@Component
public class CategoryServiceImple implements ICategoryService{

	
	@Autowired
	private ICategoryDao iCategoryDao;

	@Override
	public String add(Category category) {
		// TODO Auto-generated method stub
		return iCategoryDao.add(category);
	}

	@Override
	public List<Category> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iCategoryDao.find(qo);
	}

	@Override
	public int update(Category category) {
		// TODO Auto-generated method stub
		return iCategoryDao.updateById(category, MongoBaseDaoImple.CATEGORY);
	}

	@Override
	public List<Category> findByShop(String sunwouId) {
		Criteria c=new Criteria();
		c.and("shopId").is(sunwouId).and("isDelete").is(false);
		return iCategoryDao.getMongoTemplate().find(new Query(c), MongoBaseDaoImple.classes.get(MongoBaseDaoImple.CATEGORY));
	}

	@Override
	public Category findById(String categoryId) {
		// TODO Auto-generated method stub
		return iCategoryDao.findById(categoryId, MongoBaseDaoImple.CATEGORY);
	}
	
}
