package sunwou.mongo.daoimple;

import org.springframework.stereotype.Component;

import sunwou.entity.WithdrawalsLog;
import sunwou.mongo.dao.IWithdrawalsLogDao;
import sunwou.mongo.util.MongoBaseDaoImple;

@Component
public class WithdrawalsLogDaoImple extends MongoBaseDaoImple<WithdrawalsLog> implements IWithdrawalsLogDao{

}
