package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.Evaluate;
import sunwou.mongo.dao.IEvaluateDao;
import sunwou.mongo.util.MongoBaseDaoImple;
@Component
public class EvaluateDaoImple extends MongoBaseDaoImple<Evaluate> implements IEvaluateDao{

}
