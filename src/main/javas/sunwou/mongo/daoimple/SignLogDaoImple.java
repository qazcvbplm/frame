package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.SignLog;
import sunwou.mongo.dao.ISignLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class SignLogDaoImple extends MongoBaseDaoImple<SignLog> implements ISignLogDao{

}
