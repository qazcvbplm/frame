package sunwou.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sunwou.entity.User;
import sunwou.mongo.util.QueryObject;

public interface IUserService {

	User findByOpenId(String openid);

	User add(String openid);

	int update(User user);

	List<User> findUser(QueryObject qo);

	User findById(String sunwouId);

	int count(QueryObject qo);

	int addSource(String userId, int intValue, String c);

	int activeCount(String schoolId);

	int Money(String userId, BigDecimal senderFloorMoney, boolean b);

	int detailMoney(HttpServletRequest request);


}
