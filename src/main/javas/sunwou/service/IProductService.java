package sunwou.service;

import java.util.List;

import sunwou.entity.Product;
import sunwou.mongo.util.QueryObject;

public interface IProductService {

	String add(Product product);

	List<Product> find(QueryObject qo);

	int update(Product product);

}
