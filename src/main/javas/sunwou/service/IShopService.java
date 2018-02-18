package sunwou.service;

import java.util.List;

import sunwou.entity.Shop;
import sunwou.mongo.util.QueryObject;
import sunwou.valueobject.SchoolLoginParamObject;
import sunwou.valueobject.ShopLoginParamsObject;

public interface IShopService {

	String add(Shop shop);

	int update(Shop shop);

	List<Shop> find(QueryObject qo);

	int count(QueryObject qo);

	Shop findById(String sunwouId);


	Shop login(ShopLoginParamsObject slpo);


}
