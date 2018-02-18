package sunwou.service;

import java.math.BigDecimal;
import java.util.List;

import sunwou.entity.App;
import sunwou.entity.Order;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.util.QueryObject;
import sunwou.valueobject.AddTakeOutParamsObject;
import sunwou.valueobject.SenderStatisticsByTime;
import sunwou.valueobject.ShopStatisticsByTime;

public interface IOrderService {

	String add(AddTakeOutParamsObject aop, App app);

	Order findById(String sunwouId);

	int update(Order o);

	List<Order> find(QueryObject qo);

	int count(QueryObject qo);

	int paysuccess(Order order);

	int clear();

	List<Order> findBySenderDJS(Sender sender);

	List<Order> findByShopDJS(Shop shop);

	int toDaySchoolOrderCount(String schoolId);

	BigDecimal schoolToDayTransactionMoney(String schoolId);

	int senderAcceptOrder(Order order, Sender sender,App app,User user);

	List<Order> findorderToday(Shop shop);

	List<Order> findorderToday(Sender sender);

	int waternumber();

	void statisticsbytimeandshop(String shopId, String startTime, String endTime, ShopStatisticsByTime sbt);

	void statisticsbytimeandsender(String senderId, String startTime, String endTime, SenderStatisticsByTime sbt);

}
