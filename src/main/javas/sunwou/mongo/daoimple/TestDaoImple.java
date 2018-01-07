package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Test;
import sunwou.mongo.dao.TestDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class TestDaoImple extends MongoBaseDaoImple<Test> implements TestDao{

}
