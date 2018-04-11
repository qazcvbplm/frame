package sunwou.service;

import java.util.List;

import sunwou.entity.Evaluate;
import sunwou.mongo.util.QueryObject;

public interface IEvaluateService {

	String add(Evaluate evaluate);

	List<Evaluate> find(QueryObject qo);

	int count(QueryObject qo);

	List<Evaluate> findByShop(String shopId);

}
