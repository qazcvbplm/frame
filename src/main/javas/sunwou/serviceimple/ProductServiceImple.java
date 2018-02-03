package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sunwou.entity.Category;
import sunwou.entity.Product;
import sunwou.exception.MyException;
import sunwou.mongo.dao.ICategoryDao;
import sunwou.mongo.dao.IProductDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IProductService;

@Component
public class ProductServiceImple implements IProductService{
	
	@Autowired
	private IProductDao iProductDao;
	@Autowired
	private ICategoryDao iCategoryDao;

	@Override
	public String add(Product product) {
		// TODO Auto-generated method stub
		Category c=iCategoryDao.findById(product.getCategoryId(), MongoBaseDaoImple.CATEGORY);
		if(c==null){
			throw new MyException("分类id不存在");
		}
		product.setShopId(c.getShopId());
		product.add();
		return iProductDao.add(product);
	}

	@Override
	public List<Product> find(QueryObject qo) {
		// TODO Auto-generated method stub
		return iProductDao.find(qo);
	}

	@Override
	public int update(Product product) {
		// TODO Auto-generated method stub
		product.update();
		return iProductDao.updateById(product, MongoBaseDaoImple.PRODUCT);
	}

}
