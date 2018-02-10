package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Sender;
import sunwou.mongo.dao.ISenderDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class SenderDaoImple extends MongoBaseDaoImple<Sender> implements ISenderDao{

}
