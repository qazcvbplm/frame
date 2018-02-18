package sunwou.service;

import java.util.List;

import sunwou.entity.Sender;
import sunwou.entity.ShopApply;
import sunwou.mongo.util.QueryObject;

public interface IShopApplyService {

	String add(ShopApply shopApply);

	int count(QueryObject qo);

	List<ShopApply> find(QueryObject qo);

	int update(ShopApply shopApply);

	ShopApply findById(String sunwouId);


}
