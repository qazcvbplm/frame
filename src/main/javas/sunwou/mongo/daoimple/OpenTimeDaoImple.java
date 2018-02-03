package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.OpenTime;
import sunwou.mongo.dao.IOpenTimeDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class OpenTimeDaoImple extends MongoBaseDaoImple<OpenTime> implements IOpenTimeDao{

}
