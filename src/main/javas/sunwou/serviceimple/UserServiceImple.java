package sunwou.serviceimple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import sunwou.entity.User;
import sunwou.mongo.dao.IUserDao;
import sunwou.mongo.util.MongoBaseDaoImple;
import sunwou.mongo.util.QueryObject;
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

	@Override
	public int update(User user) {
		return iUserDao.updateById(user, MongoBaseDaoImple.USER);
	}

	@Override
	public List<User> findUser(QueryObject qo) {
		// TODO Auto-generated method stub
		return iUserDao.find(qo);
	}

	@Override
	public User findById(String sunwouId) {
		// TODO Auto-generated method stub
		return iUserDao.findById(sunwouId, MongoBaseDaoImple.USER);
	}

	@Override
	public int count(QueryObject qo) {
		// TODO Auto-generated method stub
		return iUserDao.count(qo);
	}


}
