package sunwou.service;

import java.util.List;

import sunwou.entity.Category;
import sunwou.mongo.util.QueryObject;

public interface ICategoryService {

	String add(Category category);

	List<Category> find(QueryObject qo);

	int update(Category category);

	List<Category> findByShop(String sunwouId);

}
