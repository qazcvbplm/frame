package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.App;
import sunwou.mongo.dao.TestDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class TestDaoImple extends MongoBaseDaoImple<App> implements TestDao{

}
