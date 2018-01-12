package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.User;
import sunwou.mongo.dao.IUserDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.service.IUserService;
@Component
public class UserServiceImple implements IUserService{
	@Autowired
	private IUserDao iUserDao;

	@Override
	public User findByOpenId(String openid) {
        List<User> users=iUserDao.getMongoTemplate().find(new Query(Criteria.where("openid").is(openid)),
              MongoBaseDaoImple.classes.get(MongoBaseDaoImple.USER));
        if(users.size()==0)
		return null;
        else
        return users.get(0);
	}

	@Override
	public User add(String openid) {
		User user=new User(openid);
		iUserDao.add(user);
		return user;
	}

}
