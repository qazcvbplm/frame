package sunwou.service;

import java.util.List;

import sunwou.entity.User;
import sunwou.mongo.util.QueryObject;

public interface IUserService {

	User findByOpenId(String openid);

	User add(String openid);

	int update(User user);

	List<User> findUser(QueryObject qo);

	User findById(String sunwouId);

	int count(QueryObject qo);


}
