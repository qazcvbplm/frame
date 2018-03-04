package sunwou.service;

import java.math.BigDecimal;
import java.util.List;

import sunwou.entity.Order;
import sunwou.entity.Sender;
import sunwou.mongo.util.QueryObject;

public interface ISenderService {

	String add(Sender sender);

	List<Sender> find(QueryObject qo);

	int count(QueryObject qo);

	int update(Sender sender);

	Sender findById(String sunwouId);

	int money(String senderId, BigDecimal amount, boolean b);


}
