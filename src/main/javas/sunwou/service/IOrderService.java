package sunwou.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sunwou.entity.App;
import sunwou.entity.DayLog;
import sunwou.entity.Order;
import sunwou.entity.Sender;
import sunwou.entity.Shop;
import sunwou.entity.User;
import sunwou.mongo.util.QueryObject;
import sunwou.valueobject.AddRunParamsObject;
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

	int waternumber(String shopId);

	void statisticsbytimeandshop(String shopId, String startTime, String endTime, ShopStatisticsByTime sbt);

	void statisticsbytimeandsender(String senderId, String startTime, String endTime, SenderStatisticsByTime sbt);

	List<Order> timeOutProcess();

	void shopDayLog(String day, DayLog dayLogshop);

	int takeOutComplete(Order order, HttpServletRequest request);

	int cancel(Order order);

	String addRun(AddRunParamsObject aop, App app);

	List<Order> findorderRunToday(Sender sender);

	void senderDayLog(String day, DayLog dayLogsender);

	void takeoutRemind();

	DayLog tj(QueryObject qo);

}
