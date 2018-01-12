package sunwou.service;

import sunwou.entity.User;

public interface IUserService {

	User findByOpenId(String openid);

	User add(String openid);

}
