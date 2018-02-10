package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Apply;
import sunwou.mongo.dao.IApplyDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class ApplyDaoImple  extends MongoBaseDaoImple<Apply> implements IApplyDao{

}
