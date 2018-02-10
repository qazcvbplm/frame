package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Order;
import sunwou.mongo.dao.IOrderDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class OrderDaoImple extends MongoBaseDaoImple<Order> implements IOrderDao{

}
