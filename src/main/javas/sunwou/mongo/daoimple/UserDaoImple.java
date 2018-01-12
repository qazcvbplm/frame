package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.User;
import sunwou.mongo.dao.IUserDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class UserDaoImple extends MongoBaseDaoImple<User> implements IUserDao{

}
