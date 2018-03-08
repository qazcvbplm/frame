package sunwou.service;

import java.util.List;

import sunwou.entity.OrderProduct;
import sunwou.entity.Product;
import sunwou.entity.Shop;
import sunwou.mongo.util.QueryObject;

public interface IProductService {

	String add(Product product);

	List<Product> find(QueryObject qo);

	int update(Product product);

	String minDiscount(Shop s);

	void salesadd(List<OrderProduct> orderProduct);

	Product findbyId(String string);

	int count(QueryObject qo);

}
