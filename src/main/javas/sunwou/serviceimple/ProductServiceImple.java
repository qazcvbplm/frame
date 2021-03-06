package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
import sunwou.entity.Shop;
import sunwou.mongo.daoimple.ProductDaoImple;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
import sunwou.service.IProductService;
import sunwou.service.IShopService;

@Component
public class ProductServiceImple implements IProductService{
	
	@Autowired
	private ProductDaoImple iProductDao;
	@Autowired
	private IShopService iShopSerive;

	@Override
	public String add(Product product) {
		
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
		return iProductDao.updateById(product);
	}

	@Override
	public String minDiscount(Shop s) {
		Criteria c=new Criteria();
		c.and("shopId").is(s.getSunwouId()).and("discount").lt("1").and("isDelete").is(false);
		List<Product> rs=iProductDao.getMongoTemplate().find(new Query(c).limit(1).with(
				new Sort(Direction.ASC, "discount")), iProductDao.getCl());
		if(rs.size()>0){
			return rs.get(0).getDiscount().toString();
		}else
		{
			
			return null;
		}
	}

	@Override
	public void salesadd(List<OrderProduct> orderProduct) {
		 Product p;
		 int add=0;
		 String shopId=null;
		 for(OrderProduct temp:orderProduct){
			 p=iProductDao.findById(temp.getProduct().getSunwouId());
			 shopId=temp.getProduct().getShopId();
			 add+=temp.getNumber();
			 p.setSales(p.getSales()+temp.getNumber());
			 iProductDao.updateById(p);
		 }
		 Shop shop=iShopSerive.findById(shopId);
		 shop.setSales(shop.getSales()+add);
		 shop.setRate(null);
		 shop.setProductDiscountRate(null);
		 shop.setFullCutRate(null);
		 iShopSerive.update(shop);
	}

	@Override
	public Product findbyId(String string) {
		// TODO Auto-generated method stub
		return iProductDao.findById(string);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iProductDao.count(qo);
	}

}
