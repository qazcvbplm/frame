package sunwou.service;

import java.util.List;

import sunwou.entity.App;
import sunwou.entity.Order;
import sunwou.mongo.util.QueryObject;
import sunwou.valueobject.AddTakeOutParamsObject;

public interface IOrderService {

	String add(AddTakeOutParamsObject aop, App app);

	Order findById(String sunwouId);

	int update(Order o);

	List<Order> find(QueryObject qo);

	int count(QueryObject qo);

	int paysuccess(Order order);

	int clear();

}
